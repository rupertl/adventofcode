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


;; Model the game as a map of characters with spells, mana spent and
;; mode (for part 2). Spells pending are held in a vector, entry 0
;; being a list of spell functions to run for that turn.

(def wizard-start {:hp 50 :damage 0 :armour 0 :mana 500})

(defn start-wizsim [boss]
  {:player wizard-start
   :boss boss
   :spells []
   :mana-spent 0
   :mode :easy})

;; Boss fights are as before

(defn boss-fight-turn [game]
  (update-in game [:player :hp]
             (fn [hp a d] (- hp (max 1 (- (:damage a) (:armour d)))))
             (:boss game) (:player game)))

;; Model the effect of each spell

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

;; Add a spell function for some turns

(defn add-spell-turn [game spell turn]
  (update-in game [:spells turn] #(conj % spell)))

(defn add-spell-finally [game spell turn]
  (if spell
    (update-in game [:spells turn] #(conj % spell))
    game))

(defn add-spell-turns [game spell r]
  (reduce #(add-spell-turn %1 spell %2) game r))

;; Define spell costs and durations

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

;; The player is dead if they chose to cast a spell while it is in
;; effect.
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

(defn damage-player-if-hard [game]
  (if (= (:mode game) :hard)
    (update-in game [:player :hp] #(- % 1))
    game))

;; Return the cost if player won the fight, else false.

(defn player-wins-fight-cost [game spell-list]
  (let [g0 (damage-player-if-hard game)
        g1 (apply-spells g0)
        g2 (cast-spell g1 (first spell-list))
        g3 (apply-spells g2)
        g4 (advance-turn g3)
        g5 (apply-spells g4)
        g6 (boss-fight-turn g5)
        g7 (advance-turn g6)]
    (cond (player-dead? g0) false
          (boss-dead? g1) (:mana-spent g1)
          (player-dead? g2) false
          (boss-dead? g3) (:mana-spent g3)
          (boss-dead? g5) (:mana-spent g5)
          (player-dead? g6) false
          :else (player-wins-fight-cost g7 (rest spell-list))
          )))

;; To find the min cost it looks like we can try a large number of
;; random spell combinations and just take the min, as most games
;; finish quickly and we are looking for the shortest route normally.

(defn random-spells []
  (repeatedly #(rand-nth (keys spells))))

(defn min-mana-spent-win [boss tries]
  (apply min
         (filter number?
                 (repeatedly tries #(player-wins-fight-cost (start-wizsim boss)
                                                            (random-spells))))))

;; Part Two

;; On the next run through the game, you increase the difficulty to
;; hard.

;; At the start of each player turn (before any other effects apply),
;; you lose 1 hit point. If this brings you to or below 0 hit points,
;; you lose.

;; With the same starting stats for you and the boss, what is the
;; least amount of mana you can spend and still win the fight?

;; Interestingly this seems to require more tries to get right,
;; probably because more finish earlier.

(defn hard-min-mana-spent-win [boss tries]
  (apply min
         (filter number?
                 (repeatedly tries
                             #(player-wins-fight-cost (assoc (start-wizsim boss)
                                                             :mode :hard)
                                                      (random-spells))))))
