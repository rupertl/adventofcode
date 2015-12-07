(ns adventofcode.day07-test
  (:require [adventofcode.day07 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, here is a simple circuit:

;; 123 -> x
;; 456 -> y
;; x AND y -> d
;; x OR y -> e
;; x LSHIFT 2 -> f
;; y RSHIFT 2 -> g
;; NOT x -> h
;; NOT y -> i

;; After it is run, these are the signals on the wires:

;; d: 72
;; e: 507
;; f: 492
;; g: 114
;; h: 65412
;; i: 65079
;; x: 123
;; y: 456

;; (deftest on-four
;;   (is (= 4 (house-lights-lit [[499 499 500 500 :on]]))))

(def test-circuit
  {:x [123]
   :xx [:x]
   :y [456]
   :d [AND :x :y]
   :e [OR :x :y]
   :f [LSHIFT :x 2]
   :g [RSHIFT :y 2]
   :h [NOT :x]
   :i [NOT :y]})

(deftest test-wire-d
  (is (= 72 (wire-value test-circuit :d))))

(deftest test-wire-e
  (is (= 507 (wire-value test-circuit :e))))

(deftest test-wire-f
  (is (= 492 (wire-value test-circuit :f))))

(deftest test-wire-g
  (is (= 114 (wire-value test-circuit :g))))

(deftest test-wire-h
  (is (= 65412 (wire-value test-circuit :h))))

(deftest test-wire-i
  (is (= 65079 (wire-value test-circuit :i))))

(deftest test-wire-x
  (is (= 123 (wire-value test-circuit :x))))

(deftest test-wire-y
  (is (= 456 (wire-value test-circuit :y))))

(deftest test-wire-xx
  (is (= 123 (wire-value test-circuit :xx))))
