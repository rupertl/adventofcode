(ns adventofcode.day22-test
  (:require [adventofcode.day22 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, suppose the player has 10 hit points and 250 mana, and
;; that the boss has 13 hit points and 8 damage:

;; -- Player turn --
;; - Player has 10 hit points, 0 armor, 250 mana
;; - Boss has 13 hit points
;; Player casts Poison.

;; -- Boss turn --
;; - Player has 10 hit points, 0 armor, 77 mana
;; - Boss has 13 hit points
;; Poison deals 3 damage; its timer is now 5.
;; Boss attacks for 8 damage.

;; -- Player turn --
;; - Player has 2 hit points, 0 armor, 77 mana
;; - Boss has 10 hit points
;; Poison deals 3 damage; its timer is now 4.
;; Player casts Magic Missile, dealing 4 damage.

;; -- Boss turn --
;; - Player has 2 hit points, 0 armor, 24 mana
;; - Boss has 3 hit points
;; Poison deals 3 damage. This kills the boss, and the player wins.

;; Now, suppose the same initial conditions, except that the boss has
;; 14 hit points instead:

;; -- Player turn --
;; - Player has 10 hit points, 0 armor, 250 mana
;; - Boss has 14 hit points
;; Player casts Recharge.

;; -- Boss turn --
;; - Player has 10 hit points, 0 armor, 21 mana
;; - Boss has 14 hit points
;; Recharge provides 101 mana; its timer is now 4.
;; Boss attacks for 8 damage!

;; -- Player turn --
;; - Player has 2 hit points, 0 armor, 122 mana
;; - Boss has 14 hit points
;; Recharge provides 101 mana; its timer is now 3.
;; Player casts Shield, increasing armor by 7.

;; -- Boss turn --
;; - Player has 2 hit points, 7 armor, 110 mana
;; - Boss has 14 hit points
;; Shield's timer is now 5.
;; Recharge provides 101 mana; its timer is now 2.
;; Boss attacks for 8 - 7 = 1 damage!

;; -- Player turn --
;; - Player has 1 hit point, 7 armor, 211 mana
;; - Boss has 14 hit points
;; Shield's timer is now 4.
;; Recharge provides 101 mana; its timer is now 1.
;; Player casts Drain, dealing 2 damage, and healing 2 hit points.

;; -- Boss turn --
;; - Player has 3 hit points, 7 armor, 239 mana
;; - Boss has 12 hit points
;; Shield's timer is now 3.
;; Recharge provides 101 mana; its timer is now 0.
;; Recharge wears off.
;; Boss attacks for 8 - 7 = 1 damage!

;; -- Player turn --
;; - Player has 2 hit points, 7 armor, 340 mana
;; - Boss has 12 hit points
;; Shield's timer is now 2.
;; Player casts Poison.

;; -- Boss turn --
;; - Player has 2 hit points, 7 armor, 167 mana
;; - Boss has 12 hit points
;; Shield's timer is now 1.
;; Poison deals 3 damage; its timer is now 5.
;; Boss attacks for 8 - 7 = 1 damage!

;; -- Player turn --
;; - Player has 1 hit point, 7 armor, 167 mana
;; - Boss has 9 hit points
;; Shield's timer is now 0.
;; Shield wears off, decreasing armor by 7.
;; Poison deals 3 damage; its timer is now 4.
;; Player casts Magic Missile, dealing 4 damage.

;; -- Boss turn --
;; - Player has 1 hit point, 0 armor, 114 mana
;; - Boss has 2 hit points
;; Poison deals 3 damage. This kills the boss, and the player wins.


(defn test-start-wizsim-1 []
  {:player {:hp 10 :damage 0 :armour 0 :mana 250}
   :boss {:hp 13 :damage 8 :armour 0}
   :spells []
   :mana-spent 0})

(deftest test-wiz-1
  (is (= (+ 173 53) (player-wins-fight-cost (test-start-wizsim-1)
                                            [:poison :magic-missile]))))


(defn test-start-wizsim-2 []
  {:player {:hp 10 :damage 0 :armour 0 :mana 250}
   :boss {:hp 14 :damage 8 :armour 0}
   :spells []
   :mana-spent 0})

(deftest test-wiz-2
  (is (= (+ 229 113 73 173 53)
         (player-wins-fight-cost (test-start-wizsim-2)
                                 [:recharge :shield :drain
                                  :poison :magic-missile]))))

(defn test-start-wizsim-3 []
  {:player {:hp 10 :damage 0 :armour 0 :mana 250}
   :boss {:hp 30 :damage 8 :armour 0}
   :spells []
   :mana-spent 0})

(deftest test-wiz-3
  (is (not (player-wins-fight-cost (test-start-wizsim-3)
                                   [:recharge :shield :drain
                                    :poison :magic-missile]))))
