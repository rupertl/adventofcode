(ns adventofcode.day23-test
  (:require [adventofcode.day23 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, this program sets a to 2, because the jio instruction
;; causes it to skip the tpl instruction:

;; inc a
;; jio a, +2
;; tpl a
;; inc a

(def test-op-1 [[op-inc :a] [op-jio :a +2] [op-tpl :a] [op-inc :a]])

(deftest test-pgm-1-a
  (is (= 2 (reg-when-done (start-cpu test-op-1) :a))))

(deftest test-pgm-1-b
  (is (= 0 (reg-when-done (start-cpu test-op-1) :b))))


(def test-op-2 [[op-inc :a] [op-jio :b +2] [op-tpl :a] [op-inc :a]])

(deftest test-pgm-2-a
  (is (= 4 (reg-when-done (start-cpu test-op-2) :a))))


;; Part Two

(deftest test-pgm-1-a-2
  (is (= 7 (reg-when-done-a1 (start-cpu test-op-1) :a))))
