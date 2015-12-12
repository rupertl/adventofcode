(ns adventofcode.day12-test
  (:require [adventofcode.day12 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example:

;; * [1,2,3] and {"a":2,"b":4} both have a sum of 6.
;; * [[[3]]] and {"a":{"b":4},"c":-1} both have a sum of 3.
;; * {"a":[-1,1]} and [-1,{"a":1}] both have a sum of 0.
;; * [] and {} both have a sum of 0.

(deftest test-accounting-sum-1
  (is (= 6 (accounting-sum "[1,2,3]"))))

(deftest test-accounting-sum-2
  (is (= 3 (accounting-sum "{\"a\":{\"b\":4},\"c\":-1}"))))

(deftest test-accounting-sum-3
  (is (= 3 (accounting-sum "[[[3]]]"))))

(deftest test-accounting-sum-4
  (is (= 3 (accounting-sum "{\"a\":{\"b\":4},\"c\":-1}"))))

(deftest test-accounting-sum-5
  (is (= 0 (accounting-sum "[]"))))

;; Part Two

;; For example:

;; - [1,2,3] still has a sum of 6.
;; - [1,{"c":"red","b":2},3] now has a sum of 4, because the middle
;;   object is ignored.
;; - {"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the
;;   entire structure is ignored.
;; - [1,"red",5] has a sum of 6, because "red" in an array has no effect.

(deftest test-accounting-sum-no-red-1
  (is (= 6 (accounting-sum-no-red "[1,2,3]"))))

(deftest test-accounting-sum-no-red-2
  (is (= 4 (accounting-sum-no-red "[1,{\"c\":\"red\",\"b\":2},3]"))))

(deftest test-accounting-sum-no-red-3
  (is (= 0 (accounting-sum-no-red "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"))))

(deftest test-accounting-sum-no-red-4
  (is (= 6 (accounting-sum-no-red "[1,\"red\",5]"))))
