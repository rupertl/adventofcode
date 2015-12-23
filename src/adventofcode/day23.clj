(ns adventofcode.day23
  (:gen-class))

;; Day 23: Opening the Turing Lock

;; Little Jane Marie just got her very first computer for Christmas
;; from some unknown benefactor. It comes with instructions and an
;; example program, but the computer itself seems to be
;; malfunctioning. She's curious what the program does, and would like
;; you to help her run it.

;; The manual explains that the computer supports two registers and
;; six instructions (truly, it goes on to remind the reader, a
;; state-of-the-art technology). The registers are named a and b, can
;; hold any non-negative integer, and begin with a value of 0. The
;; instructions are as follows:

;; - hlf r sets register r to half its current value, then continues
;;   with the next instruction.

;; - tpl r sets register r to triple its current value, then continues
;;   with the next instruction.

;; - inc r increments register r, adding 1 to it, then continues with
;;   the next instruction.

;; - jmp offset is a jump; it continues with the instruction offset
;;   away relative to itself.

;; - jie r, offset is like jmp, but only jumps if register r is even
;;   ("jump if even").

;; - jio r, offset is like jmp, but only jumps if register r is 1
;;   ("jump if one", not odd).

;; All three jump instructions work with an offset relative to that
;; instruction. The offset is always written with a prefix + or - to
;; indicate the direction of the jump (forward or backward,
;; respectively). For example, jmp +1 would simply continue with the
;; next instruction, while jmp +0 would continuously jump back to
;; itself forever.

;; The program exits when it tries to run an instruction beyond the
;; ones defined.

;; What is the value in register b when the program in your puzzle
;; input is finished executing?


;; The code is a map of registers, instructions and a program counter.

;; We'll structure the instructions as code, eg
;; [[op-inc :a] [op-jio :a +2] [op-tpl :a] [op-inc :a]]

(defn start-cpu [instruction]
  {:pc 0
   :a 0
   :b 0
   :instructions instruction})

;; Non jumps do this each turn
(defn advance-pc [cpu]
  (update cpu :pc #(inc %)))

;; Do I round or truncate? Chose round, which seemed OK with puzzle
;; input.
(defn op-hlf [cpu [r]]
  (advance-pc (update cpu r #(java.lang.Math/round (double (/ % 2))))))

(defn op-tpl [cpu [r]]
  (advance-pc (update cpu r #(* % 3))))

(defn op-inc [cpu [r]]
  (advance-pc (update cpu r #(inc %))))

(defn op-jmp [cpu [o]]
  (update cpu :pc #(+ % o)))

(defn op-jie [cpu [r o]]
  (if (even? (r cpu))
    (op-jmp cpu [o])
    (advance-pc cpu)))

(defn op-jio [cpu [r o]]
  (if (= 1 (r cpu))
    (op-jmp cpu [o])
    (advance-pc cpu)))

;; Should check for underflow on instructions just to be careful.
(defn cpu-stopped? [cpu]
  (or (< (:pc cpu) 0) (>= (:pc cpu) (count (:instructions cpu)))))

;; Run the instruction pointed to by the PC, and return an updated CPU.
(defn run-instruction [cpu]
  (let [op (nth (:instructions cpu) (:pc cpu))
        opcode (first op)
        operands (rest op)]
    (opcode cpu operands)))

(defn run-cpu [cpu]
  (if (cpu-stopped? cpu) cpu (recur (run-instruction cpu))))

(defn reg-when-done [cpu r]
  (r (run-cpu cpu)))


;; Part Two

;; The unknown benefactor is very thankful for releasi-- er, helping
;; little Jane Marie with her computer. Definitely not to distract
;; you, what is the value in register b after the program is finished
;; executing if register a starts as 1 instead?


;; Just override A at start

(defn reg-when-done-a1 [cpu r]
  (r (run-cpu (assoc cpu :a 1))))
