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
            [adventofcode.day11 :refer :all]
            [adventofcode.day12 :refer :all]
            [adventofcode.day13 :refer :all]
            [adventofcode.day14 :refer :all]
            [adventofcode.day15 :refer :all]
            [adventofcode.day16 :refer :all]
            [adventofcode.day17 :refer :all]
            [adventofcode.day18 :refer :all]
            [adventofcode.day19 :refer :all]
            [adventofcode.day20 :refer :all]
            [adventofcode.day21 :refer :all]
            [adventofcode.day22 :refer :all]
            [adventofcode.day23 :refer :all])
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
  (println "Day 11 Part 1: " (next-valid-password (next-valid-password input-11)))
  (println "Day 12 Part 1: " (accounting-sum input-12))
  (println "Day 12 Part 2: " (accounting-sum-no-red input-12))
  (println "Day 13 Part 1: " (best-total-happiness input-13))
  (println "Day 13 Part 2: " (best-total-happiness (add-me-to-dinner input-13)))
  (println "Day 14 Part 1: " (max-reindeer-distance input-14 2503))
  (println "Day 14 Part 2: " (max-reindeer-score input-14 2503))
  (println "Day 15 Part 1: " (max-cookie-score input-15))
  (println "Day 15 Part 2: " (max-cookie-score-500-cals input-15))
  (println "Day 16 Part 1: " (which-sue-matches-1 input-16 input-16-sue-facts))
  (println "Day 16 Part 2: " (which-sue-matches-2 input-16 input-16-sue-facts))
  (println "Day 17 Part 1: " (how-many-eggnog-containers input-17 150))
  (println "Day 17 Part 2: " (how-many-min-eggnog-containers input-17 150))
  (println "Day 18 Part 1: " (count-lights-after-steps input-18 100))
  (println "Day 18 Part 2: " (count-lights-after-steps-2 input-18 100))
  (println "Day 19 Part 1: " (calibrate-rnrffm-count input-19))
  (println "Day 19 Part 2: " (defabricate-steps input-19))
  (println "Day 20 Part 1: " (first-house-presents input-20))
  (println "Day 20 Part 2: " (first-house-presents-2 input-20))
  (println "Day 21 Part 1: " (min-cost-winning input-21))
  (println "Day 21 Part 2: " (max-cost-losing input-21))
  (println "Day 22 Part 1: " (min-mana-spent-win input-22 100000))
  (println "Day 22 Part 2: " (hard-min-mana-spent-win input-22 1000000))
  (println "Day 23 Part 1: " (reg-when-done (start-cpu input-23) :b))
  (println "Day 23 Part 1: " (reg-when-done-a1 (start-cpu input-23) :b)))
