(ns adventofcode.day03
  (:gen-class))

;; Day 3: Perfectly Spherical Houses in a Vacuum

;; Santa is delivering presents to an infinite two-dimensional grid of
;; houses.

;; He begins by delivering a present to the house at his starting
;; location, and then an elf at the North Pole calls him via radio and
;; tells him where to move next. Moves are always exactly one house to
;; the north (^), south (v), east (>), or west (<). After each move,
;; he delivers another present to the house at his new location.

;; However, the elf back at the north pole has had a little too much
;; eggnog, and so his directions are a little off, and Santa ends up
;; visiting some houses more than once. How many houses receive at
;; least one present?

(defn move-to-house [[x y] instr]
  (cond (= instr \^) [x (inc y)]
        (= instr \v) [x (dec y)]
        (= instr \>) [(inc x) y]
        (= instr \<) [(dec x) y]
        :else [x y]))

(defn houses-delivered [moves]
  (reductions move-to-house [0 0] moves))

(defn num-houses-deliverd [moves]
  (count (set (houses-delivered moves))))

;; The next year, to speed up the process, Santa creates a robot version
;; of himself, Robo-Santa, to deliver presents with him.

;; Santa and Robo-Santa start at the same location (delivering two
;; presents to the same starting house), then take turns moving based on
;; instructions from the elf, who is eggnoggedly reading from the same
;; script as the previous year.

;; This year, how many houses receive at least one present?

(defn reverse-interleave [coll n]
  (map #(take-nth n (nthrest coll %)) (range n)))

(defn houses-delivered-2 [moves]
  (let [[santa robo] (reverse-interleave moves 2)]
    (concat
     (reductions move-to-house [0 0] santa)
     (reductions move-to-house [0 0] robo))))

(defn num-houses-deliverd-2 [moves]
  (count (set (houses-delivered-2 moves))))
