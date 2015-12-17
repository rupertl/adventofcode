(ns adventofcode.day17-test
  (:require [adventofcode.day17 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, suppose you have containers of size 20, 15, 10, 5, and
;; 5 liters. If you need to store 25 liters, there are four ways to do
;; it:

;;     15 and 10
;;     20 and 5 (the first 5)
;;     20 and 5 (the second 5)
;;     15, 5, and 5

(deftest test-eggnog-containers-25
  (is (= 4 (how-many-eggnog-containers [20 15 10 5 5] 25))))

(deftest test-eggnog-containers-25-unsorted
  (is (= 4 (how-many-eggnog-containers [5 20 10 15 5] 25))))

(deftest test-eggnog-containers-20
  (is (= 4 (how-many-eggnog-containers [20 15 10 5 5] 20))))

(deftest test-eggnog-containers-15
  (is (= 3 (how-many-eggnog-containers [20 15 10 5 5] 15))))

(deftest test-eggnog-containers-100
  (is (= 0 (how-many-eggnog-containers [20 15 10 5 5] 100))))

(deftest test-eggnog-containers-1
  (is (= 0 (how-many-eggnog-containers [20 15 10 5 5] 1))))

(deftest test-eggnog-containers-0
  (is (= 0 (how-many-eggnog-containers [20 15 10 5 5] 0))))

;; Part 2

;; In the example above, the minimum number of containers was two.
;; There were three ways to use that many containers, and so the
;; answer there would be 3.

(deftest test-min-eggnog-containers-25
  (is (= 3 (how-many-min-eggnog-containers [20 15 10 5 5] 25))))
