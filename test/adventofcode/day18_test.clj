(ns adventofcode.day18-test
  (:require [adventofcode.day18 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; Here's a few steps from an example configuration of another 6x6 grid:

;; Initial state:
;; .#.#.#
;; ...##.
;; #....#
;; ..#...
;; #.#..#
;; ####..

;; After 1 step:
;; ..##..
;; ..##.#
;; ...##.
;; ......
;; #.....
;; #.##..

;; After 2 steps:
;; ..###.
;; ......
;; ..###.
;; ......
;; .#....
;; .#....

;; After 3 steps:
;; ...#..
;; ......
;; ...#..
;; ..##..
;; ......
;; ......

;; After 4 steps:
;; ......
;; ......
;; ..##..
;; ..##..
;; ......
;; ......

;; After 4 steps, this example has four lights on.

(def test18 (grid-to-lights
             [".#.#.#"
              "...##."
              "#....#"
              "..#..."
              "#.#..#"
              "####.."
              ]))


(deftest test-lights-on-0
  (is (= 15 (count-lights-after-steps test18 0))))

(deftest test-lights-on-1
  (is (= 11 (count-lights-after-steps test18 1))))

(deftest test-lights-on-2
  (is (= 8 (count-lights-after-steps test18 2))))

(deftest test-lights-on-3
  (is (= 4 (count-lights-after-steps test18 3))))

(deftest test-lights-on-4
  (is (= 4 (count-lights-after-steps test18 4))))

;; Part Two

;; The example above will actually run like this:

;; Initial state:
;; ##.#.#
;; ...##.
;; #....#
;; ..#...
;; #.#..#
;; ####.#

;; After 1 step:
;; #.##.#
;; ####.#
;; ...##.
;; ......
;; #...#.
;; #.####

;; After 2 steps:
;; #..#.#
;; #....#
;; .#.##.
;; ...##.
;; .#..##
;; ##.###

;; After 3 steps:
;; #...##
;; ####.#
;; ..##.#
;; ......
;; ##....
;; ####.#

;; After 4 steps:
;; #.####
;; #....#
;; ...#..
;; .##...
;; #.....
;; #.#..#

;; After 5 steps:
;; ##.###
;; .##..#
;; .##...
;; .##...
;; #.#...
;; ##...#

;; After 5 steps, this example now has 17 lights on.

(deftest test-lights-on-2-0
  (is (= 17 (count-lights-after-steps-2 test18 0))))

(deftest test-lights-on-2-1
  (is (= 18 (count-lights-after-steps-2 test18 1))))

(deftest test-lights-on-2-2
  (is (= 18 (count-lights-after-steps-2 test18 2))))

(deftest test-lights-on-2-3
  (is (= 18 (count-lights-after-steps-2 test18 3))))

(deftest test-lights-on-2-4
  (is (= 14 (count-lights-after-steps-2 test18 4))))

(deftest test-lights-on-2-5
  (is (= 17 (count-lights-after-steps-2 test18 5))))
