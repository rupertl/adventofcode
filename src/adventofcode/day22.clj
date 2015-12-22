(ns adventofcode.day22
  (:gen-class))


;; Day 22: Wizard Simulator 20XX

;; Little Henry Case decides that defeating bosses with swords and
;; stuff is boring. Now he's playing the game with a wizard. Of
;; course, he gets stuck on another boss and needs your help again.

;; In this version, combat still proceeds with the player and the boss
;; taking alternating turns. The player still goes first. Now,
;; however, you don't get any equipment; instead, you must choose one
;; of your spells to cast. The first character at or below 0 hit
;; points loses.

;; Since you're a wizard, you don't get to wear armor, and you can't
;; attack normally. However, since you do magic damage, your
;; opponent's armor is ignored, and so the boss effectively has zero
;; armor as well. As before, if armor (from a spell, in this case)
;; would reduce damage below 1, it becomes 1 instead - that is, the
;; boss' attacks always deal at least 1 damage.

;; On each of your turns, you must select one of your spells to cast.
;; If you cannot afford to cast any spell, you lose. Spells cost mana;
;; you start with 500 mana, but have no maximum limit. You must have
;; enough mana to cast a spell, and its cost is immediately deducted
;; when you cast it. Your spells are Magic Missile, Drain, Shield,
;; Poison, and Recharge.

;; - Magic Missile costs 53 mana. It instantly does 4 damage.

;; - Drain costs 73 mana. It instantly does 2 damage and heals you for
;;   2 hit points.

;; - Shield costs 113 mana. It starts an effect that lasts for 6
;;   turns. While it is active, your armor is increased by 7.

;; - Poison costs 173 mana. It starts an effect that lasts for 6
;;   turns. At the start of each turn while it is active, it deals the
;;   boss 3 damage.

;; - Recharge costs 229 mana. It starts an effect that lasts for 5
;;   turns. At the start of each turn while it is active, it gives you
;;   101 new mana.

;; Effects all work the same way. Effects apply at the start of both
;; the player's turns and the boss' turns. Effects are created with a
;; timer (the number of turns they last); at the start of each turn,
;; after they apply any effect they have, their timer is decreased by
;; one. If this decreases the timer to zero, the effect ends. You
;; cannot cast a spell that would start an effect which is already
;; active. However, effects can be started on the same turn they end.

;; You start with 50 hit points and 500 mana points. The boss's actual
;; stats are in your puzzle input. What is the least amount of mana
;; you can spend and still win the fight? (Do not include mana
;; recharge effects as "spending" negative mana.)

;; Model characters (players and bosses) with maps.

(def wizard-start {:hp 50 :damage 0 :armour 0 :mana 500})

(def wizsim-min-damage {:boss 1 :player 0})

(defn start-wizsim [boss]
  {:player wizard-start
   :boss boss
   :spells []
   :mana-spent 0})

(defn nomagic-fight-turn [game attacker defender]
  (update-in game [defender :hp]
             (fn [hp a d]
               (- hp (max (attacker wizsim-min-damage)
                          (- (:damage a) (:armour d)))))
             (attacker game) (defender game)))

(defn spell-magic-missile [game]
  (update-in game [:boss :hp] #(- % 4)))

(defn spell-drain [game]
  (update-in (update-in game [:boss :hp] #(- % 2))
             [:player :hp] #(+ % 2)))

(defn spell-shield [game]
  (update-in game [:player :armour] (fn [& a] 7)))

(defn spell-shield-stop [game]
  (update-in game [:player :armour] (fn [& a] 0)))

(defn spell-poison [game]
  (update-in game [:boss :hp] #(- % 3)))

(defn spell-recharge [game]
  (update-in game [:player :mana] #(+ % 101)))

(defn add-spell-turn [game spell turn]
  (update-in game [:spells turn] #(conj % spell)))

(defn add-spell-finally [game spell turn]
  (if spell
    (update-in game [:spells turn] #(conj % spell))
    game))

(defn add-spell-turns [game spell r]
  (reduce #(add-spell-turn %1 spell %2) game r))

(def spells
  {:magic-missile {:action spell-magic-missile :duration (list 0) :cost 53}
   :drain {:action spell-drain :duration (list 0) :cost 73}
   :shield {:action spell-shield :duration (range 1 7)
            :final spell-shield-stop :final-turn 7 :cost 113}
   :poison {:action spell-poison :duration (range 1 7) :cost 173}
   :recharge {:action spell-recharge :duration (range 1 6) :cost 229}})

(defn cast-spell [game spell]
  (let [s (spell spells) r (rest (:spells game))]
    (-> game
        (assoc :spells (apply vector nil r))
        (update-in [:player :mana] #(- % (:cost s)))
        (update-in [:mana-spent] #(+ % (:cost s)))
        (add-spell-turns (:action s) (:duration s))
        (add-spell-finally (:final s) (:final-turn s)))))

(defn multiple-castings? [game]
  (some #(> % 1) (vals (frequencies (first (:spells game))))))

(defn boss-dead? [game]
  (<= (:hp (:boss game)) 0))

(defn player-dead? [game]
  (or (<= (:hp (:player game)) 0)
      (< (:mana (:player game)) 0)
      (multiple-castings? game)))

(defn apply-spells [game]
  (reduce #(%2 %1) game (first (:spells game))))

(defn advance-turn [game]
  (update-in game [:spells] (fn [s] (rest s))))

(defn player-wins-wiz-fight? [game spell-list]
  (let [g1 (apply-spells game)
        g2 (cast-spell g1 (first spell-list))
        g3 (apply-spells g2)
        g4 (advance-turn g3)
        g5 (apply-spells g4)
        g6 (nomagic-fight-turn g5 :boss :player)
        g7 (advance-turn g6)]
    (cond (boss-dead? g1) true
          (player-dead? g2) false
          (boss-dead? g3) true
          (boss-dead? g5) true
          (player-dead? g6) false
          :else (player-wins-wiz-fight? g7 (rest spell-list))
          )))
