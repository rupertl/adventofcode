(ns adventofcode.day15-test
  (:require [adventofcode.day15 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For instance, suppose you have these two ingredients:

;; Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
;; Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3

;; Then, choosing to use 44 teaspoons of butterscotch and 56 teaspoons
;; of cinnamon (because the amounts of each ingredient must add up to
;; 100) would result in a cookie with the following properties:

;;     A capacity of 44*-1 + 56*2 = 68
;;     A durability of 44*-2 + 56*3 = 80
;;     A flavor of 44*6 + 56*-2 = 152
;;     A texture of 44*3 + 56*-1 = 76

;; Multiplying these together (68 * 80 * 152 * 76, ignoring calories
;; for now) results in a total score of 62842880, which happens to be
;; the best score possible given these ingredients. If any properties
;; had produced a negative total, it would have instead become zero,
;; causing the whole score to multiply to zero.

(def test15
  {:butterscotch {:capacity -1 :durability -2 :flavor 6 :texture 3 :calories 8}
   :cinnamon {:capacity 2 :durability 3 :flavor -2 :texture -1 :calories 3}})

(deftest test-cookie-score
  (is (= 62842880 (cookie-score test15 {:butterscotch 44 :cinnamon 56}))))

(deftest test-max-cookie-score
  (is (= 62842880 (max-cookie-score test15))))

;; Part Two

;; For example, given the ingredients above, if you had instead
;; selected 40 teaspoons of butterscotch and 60 teaspoons of cinnamon
;; (which still adds to 100), the total calorie count would be 40*8 +
;; 60*3 = 500. The total score would go down, though: only 57600000,
;; the best you can do in such trying circumstances.

(deftest test-cookie-cals
  (is (has-500-cals test15 {:butterscotch 40 :cinnamon 60})))

(deftest test-cookie-score-cals
  (is (= 57600000 (cookie-score test15 {:butterscotch 40 :cinnamon 60}))))

(deftest test-max-cookie-score-500-cals
  (is (= 57600000 (max-cookie-score-500-cals test15))))
