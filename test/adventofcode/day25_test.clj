(ns adventofcode.day25-test
  (:require [adventofcode.day25 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;;So, the first few codes are filled in in this order:

;;    | 1   2   3   4   5   6
;; ---+---+---+---+---+---+---+
;;  1 |  1   3   6  10  15  21
;;  2 |  2   5   9  14  20
;;  3 |  4   8  13  19
;;  4 |  7  12  18
;;  5 | 11  17
;;  6 | 16

(deftest test-triangle-1
  (is [[1]] (gen-triangle 1)))

(deftest test-triangle-4
  (is [[1 3 6 10] [2 5 9] [4 5] [7]] (gen-triangle 4)))


;; For example, the 12th code would be written to row 4, column 2; the
;; 15th code would be written to row 1, column 5.

;;    |    1         2         3         4         5         6
;; ---+---------+---------+---------+---------+---------+---------+
;;  1 | 20151125  18749137  17289845  30943339  10071777  33511524
;;  2 | 31916031  21629792  16929656   7726640  15514188   4041754
;;  3 | 16080970   8057251   1601130   7981243  11661866  16474243
;;  4 | 24592653  32451966  21345942   9380097  10600672  31527494
;;  5 |    77061  17552253  28094349   6899651   9250759  31663883
;;  6 | 33071741   6796745  25397450  24659492   1534922  27995004

(deftest test-code-at-1-1
  (is 20151125 (find-code-rc 20151125 1 1)))

(deftest test-code-at-1-5
  (is 10071777 (find-code-rc 20151125 1 5)))

(deftest test-code-at-5-1
  (is 77061 (find-code-rc 20151125 5 1)))
