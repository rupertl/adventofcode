(ns adventofcode.day03-test
  (:require [adventofcode.day03 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; - > delivers presents to 2 houses: one at the starting location,
;;   and one to the east.
;; - ^>v< delivers presents to 4 houses in a square, including twice
;;   to the house at his starting/ending location.
;; - ^v^v^v^v^v delivers a bunch of presents to some very lucky
;;   children at only 2 houses.

(deftest test-num-houses-deliverd-1
  (is (= 2 (num-houses-deliverd ">"))))

(deftest test-num-houses-deliverd-2
  (is (= 4 (num-houses-deliverd "^>v<"))))

(deftest test-num-houses-deliverd-3
  (is (= 2 (num-houses-deliverd "^v^v^v^v^v"))))

;; Part 2

;; - ^v delivers presents to 3 houses, because Santa goes north, and then
;;   Robo-Santa goes south.
;; - ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end
;;   up back where they started.
;; - ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one
;;   direction and Robo-Santa going the other.

(deftest test-num-houses-deliverd-2-1
  (is (= 3 (num-houses-deliverd-2 "^v"))))

(deftest test-num-houses-deliverd-2-2
  (is (= 3 (num-houses-deliverd-2 "^>v<"))))

(deftest test-num-houses-deliverd-2-3
  (is (= 11 (num-houses-deliverd-2 "^v^v^v^v^v"))))
