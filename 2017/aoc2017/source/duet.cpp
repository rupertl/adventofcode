#include <deque>
#include <cctype>
#include <cstddef>
#include <cstdint>
#include <stdexcept>
#include <string>
#include <unordered_map>
#include <utility>
#include <vector>

#include "duet.hpp"
#include "parser.hpp"

// Create two deques to handle communication.
// Deque 0 is read by programID 0 and sent to by programId 1 etc
DuetChannel::DuetChannel() : channel_(2, std::deque<int64_t>{}) {}

// NOLINTNEXTLINE: bugprone-easily-swappable-parameters
void DuetChannel::send(int programId, int64_t data) {
    auto cid = sender_id(programId);
    channel_[cid].push_back(data);
}

auto DuetChannel::can_receive(int programId) const -> bool {
    auto cid = receiver_id(programId);
    return ! empty(channel_[cid]);
}

auto DuetChannel::can_any_receive() const -> bool {
    return can_receive(0) || can_receive(1);
}

auto DuetChannel::receive(int programId) -> int64_t {
    auto cid = receiver_id(programId);
    auto result = channel_[cid].front();
    channel_[cid].pop_front();
    return result;
}

auto DuetChannel::receiver_id(int programId) -> std::size_t {
    return programId == 0 ? 0U : 1U;
}

auto DuetChannel::sender_id(int programId) -> std::size_t {
    return programId == 0 ? 1U : 0U;
}

Duet::Duet(const std::vector<std::string> &source) {
    for (const auto &line : source) {
        assemble(line);
    }
}

Duet::Duet(const std::vector<std::string> &source, int programId,
           DuetChannel *channel)
    : program_id_(programId), channel_(channel) {
    if (channel_ == nullptr) {
        throw std::runtime_error("Channel cannot be null");
    }

    registers_['p' - 'a'] = program_id_;

    for (const auto &line : source) {
        assemble(line);
    }
}

void Duet::assemble(const std::string &line) {
    const static std::unordered_map<std::string, DuetOpcode> opcodeMap = {
        {"nop", DuetOpcode::nop},
        {"snd", DuetOpcode::snd},
        {"set", DuetOpcode::set},
        {"add", DuetOpcode::add},
        {"mul", DuetOpcode::mul},
        {"mod", DuetOpcode::mod},
        {"rcv", DuetOpcode::rcv},
        {"jgz", DuetOpcode::jgz},
        {"sub", DuetOpcode::sub},
        {"jnz", DuetOpcode::jnz},
    };

    auto words = parse_row<std::string>(line);
    if (words.size() < 2 || words.size() > 3) {
        throw std::runtime_error("Invalid number of operands");
    }
    // Note we don't check if the required number of operands for a
    // particular opcode is given.

    DuetInstruction inst;
    auto opcodeEntry = opcodeMap.find(words[0]);
    if (opcodeEntry == opcodeMap.end()) {
        throw std::runtime_error("Invalid opcode");
    }
    inst.opcode = opcodeEntry->second;

    for (auto i = 1U; i < words.size(); ++i) {
        inst.operands.emplace_back(words[i], registers_);
    }

    instructions_.push_back(std::move(inst));
}

DuetOperand::DuetOperand(const std::string &word,
                         std::vector<int64_t> &registers) {
    if (std::islower(word[0]) != 0) {
        is_register_ = true;
        auto index = static_cast<std::size_t>(word[0] - 'a');
        register_ = &registers.at(index);
    } else if (std::isdigit(word[0]) != 0 || word[0] == '-') {
        is_register_ = false;
        value_ = std::stoi(word);
    } else {
        throw std::runtime_error("Invalid operand");
    }
}

auto DuetOperand::get() const -> int64_t {
    if (is_register_) {
        return *register_;
    }
    return value_;
}

void DuetOperand::set(int64_t newValue) {
    if (! is_register_) {
        throw std::runtime_error("Cannot set an non-register operand");
    }
    *register_ = newValue;
}

// Run until terminated or waiting for data on an empty channel
void Duet::run() {
    while (pc_ >= 0 && pc_ < static_cast<int>(num_instructions())) {
        auto &inst = instructions_[static_cast<std::size_t>(pc_++)];
        switch (inst.opcode) {
        case DuetOpcode::nop:
            break;
        case DuetOpcode::snd:
            snd(inst);
            break;
        case DuetOpcode::set:
            inst.operands[0].set(inst.operands[1].get());
            break;
        case DuetOpcode::add:
            inst.operands[0].set(inst.operands[0].get() +
                                 inst.operands[1].get());
            break;
        case DuetOpcode::sub:
            inst.operands[0].set(inst.operands[0].get() -
                                 inst.operands[1].get());
            break;
        case DuetOpcode::mul:
            inst.operands[0].set(inst.operands[0].get() *
                                 inst.operands[1].get());
            ++mul_count_;
            break;
        case DuetOpcode::mod:
            inst.operands[0].set(inst.operands[0].get() %
                                 inst.operands[1].get());
            break;
        case DuetOpcode::rcv:
            if (rcv(inst)) {
                return;
            }
            break;
        case DuetOpcode::jgz:
            if (inst.operands[0].get() > 0) {
                jmp(inst);
            }
            break;
        case DuetOpcode::jnz:
            if (inst.operands[0].get() != 0) {
                jmp(inst);
            }
            break;
        }
    }
    // pc has gone outside the bounds of memory so treat as terminated
    pc_ = TERMINATED;
}

void Duet::snd(DuetInstruction &inst) {
    if (channel_ == nullptr) {
        // We think we are a soundcard
        sound_ = inst.operands[0].get();
    } else {
        channel_->send(program_id_, inst.operands[0].get());
        num_sends_++;
    }
}

// Recover a sound or receive a message.
// Return true if we should break after this instruction
auto Duet::rcv(DuetInstruction &inst) -> bool{
    if (channel_ == nullptr) {
        // We think we are a soundcard
        if (inst.operands[0].get() != 0) {
            recovered_ = sound_;
            return true;
        }
    } else {
        if (channel_->can_receive(program_id_)) {
            inst.operands[0].set(channel_->receive(program_id_));
        } else {
            // Blocked waiting for input
            pc_--;          // undo auto increment
            return true;
        }
    }
    return false;
}

void Duet::jmp(DuetInstruction &inst) {
    pc_--;          // undo auto increment
    pc_ += static_cast<int>(inst.operands[1].get());
}

auto Duet::register_index(char reg) -> std::size_t {
    const char maxRegister = 'a' + NUM_REGISTERS - 1;
    if (reg < 'a' || reg > maxRegister) {
        throw std::runtime_error("Register out of range");
    }
    return static_cast<std::size_t>(reg - 'a');
}

auto Duet::get_register(char reg) const -> int64_t {
    auto index = register_index(reg);
    return registers_[index];
}

// NOLINTNEXTLINE: bugprone-easily-swappable-parameters
void Duet::set_register(char reg, int64_t value) {
    auto index = register_index(reg);
    registers_[index] = value;
}
