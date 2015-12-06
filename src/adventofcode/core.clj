(ns adventofcode.core
  (:require [adventofcode.day01 :refer :all]
            [adventofcode.day02 :refer :all]
            [adventofcode.day03 :refer :all]
            [adventofcode.day04 :refer :all]
            [adventofcode.day05 :refer :all]
            [adventofcode.day06 :refer :all])
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
  (println "Day  6 Part 2: " (house-lights-lit-2 input-06)))
