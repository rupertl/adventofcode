(ns adventofcode.day06-test
  (:require [adventofcode.day06 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example:

;; * turn on 0,0 through 999,999 would turn on (or leave on) every light.
;; * toggle 0,0 through 999,0 would toggle the first line of 1000
;;   lights, turning off the ones that were on, and turning on the
;;   ones that were off.
;; * turn off 499,499 through 500,500 would turn off (or leave off)
;;   the middle four lights.

(deftest all-on
  (is (= 1000000 (house-lights-lit [[0 0 999 999 :on]]))))

(deftest toggle-first-row
  (is (= 1000 (house-lights-lit [[0 0 999 0 :toggle]]))))

(deftest on-four
  (is (= 4 (house-lights-lit [[499 499 500 500 :on]]))))

(deftest combine-instructions
  (is (= (- 1000000 (+ 1000 4))
         (house-lights-lit [[0 0 999 999 :on]
                            [0 0 999 0 :toggle]
                            [499 499 500 500 :off]]))))

;; Part 2

;; For example:

;;     turn on 0,0 through 0,0 would increase the total brightness by 1.
;;     toggle 0,0 through 999,999 would increase the total brightness by 2000000.

(deftest one-on
  (is (= 1 (house-lights-lit-2 [[0 0 0 0 :on]]))))

(deftest all-on-2
  (is (= 2000000 (house-lights-lit-2 [[0 0 999 999 :toggle]]))))
