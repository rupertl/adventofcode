(ns adventofcode.day13-test
  (:require [adventofcode.day13 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, suppose you have only four attendees planned, and you
;; calculate their potential happiness as follows:

;; Alice would gain 54 happiness units by sitting next to Bob.
;; Alice would lose 79 happiness units by sitting next to Carol.
;; Alice would lose 2 happiness units by sitting next to David.
;; Bob would gain 83 happiness units by sitting next to Alice.
;; Bob would lose 7 happiness units by sitting next to Carol.
;; Bob would lose 63 happiness units by sitting next to David.
;; Carol would lose 62 happiness units by sitting next to Alice.
;; Carol would gain 60 happiness units by sitting next to Bob.
;; Carol would gain 55 happiness units by sitting next to David.
;; David would gain 46 happiness units by sitting next to Alice.
;; David would lose 7 happiness units by sitting next to Bob.
;; David would gain 41 happiness units by sitting next to Carol.

;; Then, if you seat Alice next to David, Alice would lose 2 happiness
;; units (because David talks so much), but David would gain 46
;; happiness units (because Alice is such a good listener), for a
;; total change of 44.

;; If you continue around the table, you could then seat Bob next to
;; Alice (Bob gains 83, Alice gains 54). Finally, seat Carol, who sits
;; next to Bob (Carol gains 60, Bob loses 7) and David (Carol gains
;; 55, David gains 41). The arrangement looks like this:

;;      +41 +46
;; +55   David    -2
;; Carol       Alice
;; +60    Bob    +54
;;      -7  +83

;; After trying every other seating arrangement in this hypothetical
;; scenario, you find that this one is the most optimal, with a total
;; change in happiness of 330.

(def test13
  {
   [:Alice :Bob] +54
   [:Alice :Carol] -79
   [:Alice :David] -2
   [:Bob :Alice] +83
   [:Bob :Carol] -7
   [:Bob :David] -63
   [:Carol :Alice] -62
   [:Carol :Bob] +60
   [:Carol :David] +55
   [:David :Alice] +46
   [:David :Bob] -7
   [:David :Carol] +41})


(deftest test-dinner-names
  (is (= #{:Alice :Bob :Carol :David} (dinner-names test13))))

(deftest test-net-happiness-1
  (is (= 44 (net-happiness test13 [:Alice :David]))))

(deftest test-net-happiness-2
  (is (= 53 (net-happiness test13 [:Carol :Bob]))))

(deftest test-total-happiness
  (is (= 330 (total-happiness test13 [:Alice :Bob :Carol :David]))))

(deftest test-best-total-happiness
  (is (= 330 (best-total-happiness test13))))

;; Part Two

(deftest test-dinner-names-2
  (is (= #{:me :Alice :Bob :Carol :David}
         (dinner-names (add-me-to-dinner test13)))))

(deftest test-net-happiness-3
  (is (= 0 (net-happiness (add-me-to-dinner test13) [:Alice :me]))))
