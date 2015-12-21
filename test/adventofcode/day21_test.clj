(ns adventofcode.day21-test
  (:require [adventofcode.day21 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, suppose you have 8 hit points, 5 damage, and 5 armor,
;; and that the boss has 12 hit points, 7 damage, and 2 armor:

;;     The player deals 5-2 = 3 damage; the boss goes down to 9 hit points.
;;     The boss deals 7-5 = 2 damage; the player goes down to 6 hit points.
;;     The player deals 5-2 = 3 damage; the boss goes down to 6 hit points.
;;     The boss deals 7-5 = 2 damage; the player goes down to 4 hit points.
;;     The player deals 5-2 = 3 damage; the boss goes down to 3 hit points.
;;     The boss deals 7-5 = 2 damage; the player goes down to 2 hit points.
;;     The player deals 5-2 = 3 damage; the boss goes down to 0 hit points.

;; In this scenario, the player wins! (Barely.)

(deftest test-player-wins-fight-1
  (is (player-wins-fight? {:hp 12 :damage 7 :armour 2}
                          {:hp 8 :damage 5 :armour 5})))

(deftest test-player-loses-fight-2
  (is (false? (player-wins-fight? {:hp 12 :damage 7 :armour 2}
                                  {:hp 2 :damage 5 :armour 5}))))
