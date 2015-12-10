(ns adventofcode.day10-test
  (:require [adventofcode.day10 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example:

;; * 1 becomes 11 (1 copy of digit 1).
;; * 11 becomes 21 (2 copies of digit 1).
;; * 21 becomes 1211 (one 2 followed by one 1).
;; * 1211 becomes 111221 (one 1, one 2, and two 1s).
;; * 111221 becomes 312211 (three 1s, two 2s, and one 1).

(deftest test-look-and-say-1
  (is (= [1 1] (look-and-say [1]))))

(deftest test-look-and-say-2
  (is (= [2 1] (look-and-say [1 1]))))

(deftest test-look-and-say-3
  (is (= [1 2 1 1] (look-and-say [2 1]))))

(deftest test-look-and-say-4
  (is (= [1 1 1 2 2 1] (look-and-say [1 2 1 1]))))

(deftest test-look-and-say-5
  (is (= [3 1 2 2 1 1] (look-and-say [1 1 1 2 2 1]))))

(deftest test-length-n-look-and-say
  (is (= 6 (length-n-look-and-say [1] 4))))
