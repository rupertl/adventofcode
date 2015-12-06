(ns adventofcode.day02-test
  (:require [adventofcode.day02 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; - A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52
;;   square feet of wrapping paper plus 6 square feet of slack, for a
;;   total of 58 square feet.
;; - A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42
;;   square feet of wrapping paper plus 1 square foot of slack, for a
;;   total of 43 square feet.

(deftest test-paper-required-1
  (is (= 58 (paper-required 2 3 4))))

(deftest test-paper-required-2
  (is (= 43 (paper-required 1 1 10))))

(deftest test-total-paper-required
  (is (= (+ 43 58) (total-paper-required [[2 3 4] [1 1 10]]))))

;; - A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon
;;   to wrap the present plus 2*3*4 = 24 feet of ribbon for the bow, for
;;   a total of 34 feet.
;; - A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon
;;   to wrap the present plus 1*1*10 = 10 feet of ribbon for the bow, for
;;   a total of 14 feet.

(deftest test-ribbon-required-1
  (is (= 34 (ribbon-required [2 3 4]))))

(deftest test-ribbon-required-2
  (is (= 14 (ribbon-required [1 1 10]))))

(deftest test-total-ribbon-required
  (is (= (+ 34 14) (total-ribbon-required [[2 3 4] [1 1 10]]))))
