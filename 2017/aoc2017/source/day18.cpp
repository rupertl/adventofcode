#include <cassert>
#include <deque>
#include <cctype>
#include <cstddef>
#include <cstdint>
#include <string>
#include <unordered_map>
#include <utility>
#include <vector>

#include "parser.hpp"
#include "day18.hpp"

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
    assert(channel_ != nullptr);
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
        {"jgz", DuetOpcode::jgz}
    };
        
    auto words = parse_row<std::string>(line);
    assert(words.size() == 2 || words.size() == 3);
    // Note we don't check if the required number of operands for a
    // particular opcode is given.

    DuetInstruction inst;
    auto opcodeEntry = opcodeMap.find(words[0]);
    assert(opcodeEntry != opcodeMap.end());
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
        assert(false);
    }
}

auto DuetOperand::get() const -> int64_t {
    if (is_register_) {
        return *register_;
    }
    return value_;
}

void DuetOperand::set(int64_t newValue) {
    assert(is_register_);
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
            if (channel_ == nullptr) {
                // We think we are a soundcard
                sound_ = inst.operands[0].get();
            } else {
                channel_->send(program_id_, inst.operands[0].get());
                num_sends_++;
            }
            break;
        case DuetOpcode::set:
            inst.operands[0].set(inst.operands[1].get());
            break;
        case DuetOpcode::add:
            inst.operands[0].set(inst.operands[0].get() +
                                 inst.operands[1].get());
            break;
        case DuetOpcode::mul:
            inst.operands[0].set(inst.operands[0].get() *
                                 inst.operands[1].get());
            break;
        case DuetOpcode::mod:
            inst.operands[0].set(inst.operands[0].get() %
                                 inst.operands[1].get());
            break;
        case DuetOpcode::rcv:
            if (channel_ == nullptr) {
                // We think we are a soundcard
                if (inst.operands[0].get() != 0) {
                    recovered_ = sound_;
                    return;
                }
            } else {
                if (channel_->can_receive(program_id_)) {
                    inst.operands[0].set(channel_->receive(program_id_));
                } else {
                    // Blocked waiting for input
                    pc_--;          // undo increment above
                    return;
                }
            }
            break;
        case DuetOpcode::jgz:
            if (inst.operands[0].get() > 0) {
                pc_--;          // undo increment above
                pc_ += static_cast<int>(inst.operands[1].get());
            }
            break;
        }
    }
    // pc has gone outside the bounds of memory so treat as terminated
    pc_ = TERMINATED;
}

auto count_sent_duet_1(const std::vector<std::string> &source) -> int {
    DuetChannel dch;
    Duet duet0(source, 0, &dch);
    Duet duet1(source, 1, &dch);

    // Run until both programs are terminated or deadlocked
    while (true) {
        duet0.run();
        duet1.run();
        if (! dch.can_any_receive() ||
            (duet0.terminated() && duet1.terminated())) {
            return duet1.count_sent();
        }
    }
}

auto Day18::calculate_a() -> std::string {
    Duet crd(input_lines());
    crd.run();
    return std::to_string(crd.recovered_sound());
}

auto Day18::calculate_b() -> std::string {
    return std::to_string(count_sent_duet_1(input_lines()));
}
