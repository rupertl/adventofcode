(ns adventofcode.core
  (:require [adventofcode.day01 :refer :all]
            [adventofcode.day02 :refer :all]
            [adventofcode.day03 :refer :all]
            [adventofcode.day04 :refer :all]
            [adventofcode.day05 :refer :all]
            [adventofcode.day06 :refer :all]
            [adventofcode.day07 :refer :all]
            [adventofcode.day08 :refer :all]
            [adventofcode.day09 :refer :all]
            [adventofcode.day10 :refer :all]
            [adventofcode.day11 :refer :all])
  (:gen-class))

;; Load the test inputs as input-01, input-02 etc
;; These will be unique for each player
(load-file "resources/inputs.clj")

(defn -main
  "Runs each day's problems with test inputs"
  [& args]
  (println "Day  1 Part 1: " (which-floor input-01))
  (println "Day  1 Part 2: " (basement-instruction input-01))
  (println "Day  2 Part 1: " (total-paper-required input-02))
  (println "Day  2 Part 2: " (total-ribbon-required input-02))
  (println "Day  3 Part 1: " (num-houses-deliverd input-03))
  (println "Day  3 Part 2: " (num-houses-deliverd-2 input-03))
  (println "Day  4 Part 1: " (first-adventcoin input-04))
  (println "Day  4 Part 2: " (first-adventcoin-n input-04 6))
  (println "Day  5 Part 1: " (count-nice naughty-or-nice input-05))
  (println "Day  5 Part 2: " (count-nice naughty-or-nice-2 input-05))
  (println "Day  6 Part 1: " (house-lights-lit input-06))
  (println "Day  6 Part 2: " (house-lights-lit-2 input-06))
  (println "Day  7 Part 1: " (wire-value input-07 :a))
  (println "Day  7 Part 2: " (override-wire-b input-07 :a))
  (println "Day  8 Part 1: " (total-code-memory-difference input-08))
  (println "Day  8 Part 2: " (total-encoded-code-difference input-08))
  (println "Day  9 Part 1: " (min-trip-distance input-09))
  (println "Day  9 Part 2: " (max-trip-distance input-09))
  (println "Day 10 Part 1: " (length-n-look-and-say input-10 40))
  (println "Day 10 Part 2: " (length-n-look-and-say input-10 50))
  (println "Day 11 Part 1: " (next-valid-password input-11))
  (println "Day 11 Part 1: " (next-valid-password (next-valid-password input-11))))
