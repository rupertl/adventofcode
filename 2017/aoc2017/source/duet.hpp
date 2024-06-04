#pragma once

#include <cstdint>
#include <deque>
#include <string>
#include <vector>

// A DuetChannel connects two Duets, allowing both to send and receive
// asynchronously. Role is determined by the programId you pass in.
// Note: not thread safe!
class DuetChannel {
public:
    explicit DuetChannel();
    void send(int programId, int64_t data);
    auto can_receive(int programId) const -> bool;
    auto can_any_receive() const -> bool;
    auto receive(int programId) -> int64_t;

private:
    std::vector<std::deque<int64_t>> channel_;
    static auto receiver_id(int programId) -> std::size_t;
    static auto sender_id(int programId) -> std::size_t;
};

enum DuetOpcode {
    nop,                        // Not part of spec but handy for testing
    snd,
    set,
    add,
    mul,
    mod,
    rcv,
    jgz,
    sub,
    jnz,
};

// An operand can be a register or an immediate value. On
// construction, if a register we glue a pointer to the register
// vector so we can access it directlt. This means the lifetime of
// registers should be longer than operands.
class DuetOperand {
public:
    explicit DuetOperand(const std::string &word,
                         std::vector<int64_t> &registers);
    auto get() const -> int64_t;
    void set(int64_t newValue);

private:
    bool is_register_ = false;
    int64_t value_ = 0;
    int64_t *register_ = nullptr;
};

// An instruction is an opcode and 0 or more operands.
struct DuetInstruction {
    DuetOpcode opcode = DuetOpcode::nop;
    std::vector<DuetOperand> operands;
};

// A Duet has a ...dual role. In part 1 it is a sound card. snd means
// make a sound, rcv means recover the last sound. In part 2 there are
// multiple instances communicating over a channel via sna and rcv.
class Duet {
public:
    explicit Duet(const std::vector<std::string> &source);  // Part 1
    explicit Duet(const std::vector<std::string> &source,
                  int programId, DuetChannel *channel);     // Part 2
    auto num_instructions() const { return instructions_.size(); }
    void run();
    auto terminated() const -> bool { return pc_ == TERMINATED; }
    auto recovered_sound() const -> int64_t { return recovered_; } // Part 1
    auto count_sent() const -> int { return num_sends_; }          // Part 2
    auto mul_count() const -> int { return mul_count_; }
    auto get_register(char reg) const -> int64_t;
    void set_register(char reg, int64_t value);

private:
    static constexpr auto NUM_REGISTERS = 26U;
    static constexpr auto TERMINATED = -1;
    int program_id_ = 0;
    DuetChannel *channel_ = nullptr;
    std::vector<int64_t> registers_ = std::vector<int64_t>(NUM_REGISTERS, 0);
    int pc_ = 0;
    int64_t sound_ = 0;
    int64_t recovered_ = 0;
    int num_sends_ = 0;
    std::vector<DuetInstruction> instructions_;
    int mul_count_ = 0;

    void assemble(const std::string &line);
    void snd(DuetInstruction &inst);
    auto rcv(DuetInstruction &inst) -> bool;
    void jmp(DuetInstruction &inst);
    static auto register_index(char reg) -> std::size_t;
};

