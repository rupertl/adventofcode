(ns adventofcode.day10
  (:gen-class))

;; Day 10: Elves Look, Elves Say

;; Today, the Elves are playing a game called look-and-say. They take
;; turns making sequences by reading aloud the previous sequence and
;; using that reading as the next sequence. For example, 211 is read
;; as "one two, two ones", which becomes 1221 (1 2, 2 1s).

;; Look-and-say sequences are generated iteratively, using the
;; previous value as input for the next step. For each step, take the
;; previous value, and replace each run of digits (like 111) with the
;; number of digits (3) followed by the digit itself (1).

;; Starting with the digits in your puzzle input, apply this process
;; 40 times. What is the length of the result?

(defn look-and-say [numbers]
  (mapcat #(vector (count %) (first %))
          (partition-by identity numbers)))

(defn length-n-look-and-say [numbers n]
  (->> numbers (iterate look-and-say) (take (inc n)) last count))

;; Part 2

;; Neat, right? You might also enjoy hearing John Conway talking about
;; this sequence (that's Conway of Conway's Game of Life fame).

;; Now, starting again with the digits in your puzzle input, apply
;; this process 50 times. What is the length of the new result?
