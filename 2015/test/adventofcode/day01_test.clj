(ns adventofcode.day01-test
  (:require [adventofcode.day01 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; - (()) and ()() both result in floor 0.
;; - ((( and (()(()( both result in floor 3.
;; - ))((((( also results in floor 3.
;; - ()) and ))( both result in floor -1 (the first basement level).
;; - ))) and )())()) both result in floor -3.

(deftest test-which-floor-1
  (is (= 0 (which-floor "(())"))))

(deftest test-which-floor-2
  (is (= 0 (which-floor "(())"))))

(deftest test-which-floor-3
  (is (= 3 (which-floor "((("))))

(deftest test-which-floor-4
  (is (= 3 (which-floor "(()(()("))))

(deftest test-which-floor-5
  (is (= 3 (which-floor "))((((("))))

(deftest test-which-floor-6
  (is (= -3 (which-floor ")())())"))))

;; - ) causes him to enter the basement at character position 1.
;; - ()()) causes him to enter the basement at character position 5.

(deftest test-basement-instruction-1
  (is (= 1 (basement-instruction ")"))))

(deftest test-basement-instruction-2
  (is (= 5 (basement-instruction "()())"))))
