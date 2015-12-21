(ns adventofcode.day21
  (:require [clojure.math.combinatorics :as combo])
  (:gen-class))


;; Day 21: RPG Simulator 20XX

;; Little Henry Case got a new video game for Christmas. It's an RPG,
;; and he's stuck on a boss. He needs to know what equipment to buy at
;; the shop. He hands you the controller.

;; In this game, the player (you) and the enemy (the boss) take turns
;; attacking. The player always goes first. Each attack reduces the
;; opponent's hit points by at least 1. The first character at or
;; below 0 hit points loses.

;; Damage dealt by an attacker each turn is equal to the attacker's
;; damage score minus the defender's armor score. An attacker always
;; does at least 1 damage. So, if the attacker has a damage score of
;; 8, and the defender has an armor score of 3, the defender loses 5
;; hit points. If the defender had an armor score of 300, the defender
;; would still lose 1 hit point.

;; Your damage score and armor score both start at zero. They can be
;; increased by buying items in exchange for gold. You start with no
;; items and have as much gold as you need. Your total damage or armor
;; is equal to the sum of those stats from all of your items. You have
;; 100 hit points.

;; Here is what the item shop is selling:

;; Weapons:    Cost  Damage  Armor
;; Dagger        8     4       0
;; Shortsword   10     5       0
;; Warhammer    25     6       0
;; Longsword    40     7       0
;; Greataxe     74     8       0

;; Armor:      Cost  Damage  Armor
;; Leather      13     0       1
;; Chainmail    31     0       2
;; Splintmail   53     0       3
;; Bandedmail   75     0       4
;; Platemail   102     0       5

;; Rings:      Cost  Damage  Armor
;; Damage +1    25     1       0
;; Damage +2    50     2       0
;; Damage +3   100     3       0
;; Defense +1   20     0       1
;; Defense +2   40     0       2
;; Defense +3   80     0       3

;; You must buy exactly one weapon; no dual-wielding. Armor is
;; optional, but you can't use more than one. You can buy 0-2 rings
;; (at most one for each hand). You must use any items you buy. The
;; shop only has one of each item, so you can't buy, for example, two
;; rings of Damage +3.

;; You have 100 hit points. The boss's actual stats are in your puzzle
;; input. What is the least amount of gold you can spend and still win
;; the fight?


;; Model characters (players and bosses) with maps.

(def player-start {:hp 100 :damage 0 :armour 0 :cost 0})

;; Implement the fight as a recursive function that ends when one or
;; the other is dead.

(defn fight-turn [attacker defender]
  (assoc defender :hp (- (:hp defender)
                         (max 1 (- (:damage attacker) (:armour defender))))))

(defn character-dead? [character]
  (<= (:hp character) 0))

(defn player-wins-fight? [boss player]
  (let [b (fight-turn player boss) p (fight-turn boss player)]
    (cond (character-dead? boss) true
          (character-dead? player) false
          :else (player-wins-fight? b p))))

;; Define the shop, using a similar map to characters.

(def shop
  { :weapons
   {:Dagger {:cost 8 :damage 4 :armour 0}
    :Shortsword {:cost 10 :damage 5 :armour 0}
    :Warhammer {:cost 25 :damage 6 :armour 0}
    :Longsword {:cost 40 :damage 7 :armour 0}
    :Greataxe {:cost 74 :damage 8 :armour 0}}

   :armour
   {:Leather {:cost 13 :damage 0 :armour 1}
    :Chainmail {:cost 31 :damage 0 :armour 2}
    :Splintmail {:cost 53 :damage 0 :armour 3}
    :Bandedmail {:cost 75 :damage 0 :armour 4}
    :Platemail {:cost 102 :damage 0 :armour 5}
    :no-armour {:cost 0 :damage 0 :armour 0}}

   :rings
   {:Damage1 {:cost 25 :damage 1 :armour 0}
    :Damage2 {:cost 50 :damage 2 :armour 0}
    :Damage3 {:cost 100 :damage 3 :armour 0}
    :Defense1 {:cost 20 :damage 0 :armour 1}
    :Defense2 {:cost 40 :damage 0 :armour 2}
    :Defense3 {:cost 80 :damage 0 :armour 3}
    :no-ring {:cost 0 :damage 0 :armour 0}}})


;; Find all combinations of valid purchases using cartesian-product
;; and filtering out duplicate rings.
(defn shopping-combos []
  (remove #(and (not= (key (nth % 2)) :no-ring)
                (= (nth % 2) (nth % 3)))
          (combo/cartesian-product (:weapons shop)
                                   (:armour shop)
                                   (:rings shop) (:rings shop))))

;; Augment a player with a list of items from the shop
(defn equip-player-items [player items type]
  (list type (reduce #(+ %1 (type (second %2)))
                     (type player)
                     items)))

(defn equip-player [player items]
  (apply assoc player (mapcat (partial equip-player-items player items)
                              [:cost :damage :armour])))

;; Try fighting the boss with all combinations of players and
;; purchases to find the winndrs.
(defn winning-players [boss]
  (filter (partial player-wins-fight? boss)
          (map (partial equip-player player-start) (shopping-combos))))

(defn min-cost-winning [boss]
  (apply min (map :cost (winning-players boss))))


;; Part Two

;; Turns out the shopkeeper is working with the boss, and can persuade
;; you to buy whatever items he wants. The other rules still apply,
;; and he still only has one of each item.

;; What is the most amount of gold you can spend and still lose the
;; fight?

;; Simply replace filter with remove and min with max.

(defn losing-players [boss]
  (remove (partial player-wins-fight? boss)
          (map (partial equip-player player-start) (shopping-combos))))

(defn max-cost-losing [boss]
  (apply max (map :cost (losing-players boss))))
