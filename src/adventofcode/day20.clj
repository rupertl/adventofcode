(ns adventofcode.day20
  (:gen-class))

;; Day 20: Infinite Elves and Infinite Houses

;; To keep the Elves busy, Santa has them deliver some presents by
;; hand, door-to-door. He sends them down a street with infinite
;; houses numbered sequentially: 1, 2, 3, 4, 5, and so on.

;; Each Elf is assigned a number, too, and delivers presents to houses
;; based on that number:

;; - The first Elf (number 1) delivers presents to every house: 1, 2,
;;   3, 4, 5, ....

;; - The second Elf (number 2) delivers presents to every second
;;   house: 2, 4, 6, 8, 10, ....

;; - Elf number 3 delivers presents to every third house: 3, 6, 9, 12,
;;   15, ....

;; There are infinitely many Elves, numbered starting with 1. Each Elf
;; delivers presents equal to ten times his or her number at each
;; house.

;; What is the lowest house number of the house to get at least as
;; many presents as the number in your puzzle input?


;; So each house gets presents based on the factors of the house number.

(defn factors [n]
  (reduce #(if (zero? (rem n %2)) (conj %1 %2 (/ n %2)) %1)
          #{} (range 1 (inc (java.lang.Math/sqrt n)))))

(defn house-presents [house]
  (* 10 (apply + (factors house))))

(defn first-house-presents [target]
  (some (fn [[house presents]] (if (>= presents target) (inc house)))
        (map-indexed vector (map #(house-presents (inc %)) (range)))))


;; Part Two

;; The Elves decide they don't want to visit an infinite number of
;; houses. Instead, each Elf will stop after delivering presents to 50
;; houses. To make up for it, they decide to deliver presents equal to
;; eleven times their number at each house.

;; With these changes, what is the new lowest house number of the
;; house to get at least as many presents as the number in your puzzle
;; input?


;; Just filter out elve-factors which when multiplied by 50 are more
;; than the house number.

(defn house-presents-2 [house]
  (* 11 (apply + (filter #(<= house (* 50 %)) (factors house)))))

(defn first-house-presents-2 [target]
  (some (fn [[house presents]] (if (>= presents target) (inc house)))
        (map-indexed vector (map #(house-presents-2 (inc %)) (range)))))
