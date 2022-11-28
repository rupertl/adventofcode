(ns adventofcode.day20-test
  (:require [adventofcode.day20 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; So, the first nine houses on the street end up like this:

;; House 1 got 10 presents.
;; House 2 got 30 presents.
;; House 3 got 40 presents.
;; House 4 got 70 presents.
;; House 5 got 60 presents.
;; House 6 got 120 presents.
;; House 7 got 80 presents.
;; House 8 got 150 presents.
;; House 9 got 130 presents.

;; The first house gets 10 presents: it is visited only by Elf 1,
;; which delivers 1 * 10 = 10 presents. The fourth house gets 70
;; presents, because it is visited by Elves 1, 2, and 4, for a total
;; of 10 + 20 + 40 = 70 presents.


(deftest test-house-presents-1
  (is (= 10 (house-presents 1))))

(deftest test-house-presents-5
  (is (= 60 (house-presents 5))))

(deftest test-house-presents-6
  (is (= 120 (house-presents 6))))

(deftest test-house-presents-7
  (is (= 80 (house-presents 7))))

(deftest test-first-house-presents-120
  (is (= 6 (first-house-presents 120))))
