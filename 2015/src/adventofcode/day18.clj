(ns adventofcode.day18
  (:gen-class))

;; Day 18: Like a GIF For Your Yard

;; After the million lights incident, the fire code has gotten
;; stricter: now, at most ten thousand lights are allowed. You arrange
;; them in a 100x100 grid.

;; Never one to let you down, Santa again mails you instructions on
;; the ideal lighting configuration. With so few lights, he says,
;; you'll have to resort to animation.

;; Start by setting your lights to the included initial configuration
;; (your puzzle input). A # means "on", and a . means "off".

;; Then, animate your grid in steps, where each step decides the next
;; configuration based on the current one. Each light's next state
;; (either on or off) depends on its current state and the current
;; states of the eight lights adjacent to it (including diagonals).
;; Lights on the edge of the grid might have fewer than eight
;; neighbors; the missing ones always count as "off".

;; For example, in a simplified 6x6 grid, the light marked A has the
;; neighbors numbered 1 through 8, and the light marked B, which is on
;; an edge, only has the neighbors marked 1 through 5:

;; 1B5...
;; 234...
;; ......
;; ..123.
;; ..8A4.
;; ..765.

;; The state a light should have next is based on its current state
;; (on or off) plus the number of neighbors that are on:

;; * A light which is on stays on when 2 or 3 neighbors are on, and turns
;;   off otherwise.
;; * A light which is off turns on if exactly 3 neighbors are on, and
;;   stays off otherwise.

;; All of the lights update simultaneously; they all consider the same
;; current state before moving to the next.

;; In your grid of 100x100 lights, given your initial configuration,
;; how many lights are on after 100 steps?


;; Uses a vector of size len*len to store the lights.

(defn line-to-lights [line]
  (map #(if (= % \#) 1 0) line))

(defn grid-to-lights [lines]
  (vec (flatten (map line-to-lights lines))))

(defn lights-length [lights]
  (int (java.lang.Math/sqrt (count lights))))

(defn light-position [len x y]
  (+ x (* y len)))

(defn count-lights-on [lights]
  (apply + lights))

(defn light-at [len lights x y]
  (lights (light-position len x y)))

(defn neighbour-at [len lights x y]
  (if (or (< x 0) (>= x len) (< y 0) (>= y len))
    0
    (light-at len lights x y)))

(defn neighbour-positions [x y]
  (map (fn [[a b]] [(+ x a) (+ y b)]) [[-1 -1] [0 -1] [1 -1]
                                       [-1 0]         [1 0]
                                       [-1 1]  [0 1]  [1 1]]))

(defn count-neighbours-on [len lights x y]
  (count-lights-on (map (fn [[a b]] (neighbour-at len lights a b))
                        (neighbour-positions x y))))

(defn transition-light [len lights x y]
  (let [n (count-neighbours-on len lights x y)
        on (= 1 (light-at len lights x y)) ]
    (cond (and on (or (= n 2) (= n 3))) 1
          (and (not on) (= n 3)) 1
          :else 0)))

(defn transition-lights [len lights]
  (vec (for [y (range len) x (range len)]
         (transition-light len lights x y))))

(defn count-lights-after-steps [lights n]
  (let [len (lights-length lights)]
    (count-lights-on (nth (iterate (partial transition-lights len) lights) n))))


;; Part Two

;; You flip the instructions over; Santa goes on to point out that
;; this is all just an implementation of Conway's Game of Life. At
;; least, it was, until you notice that something's wrong with the
;; grid of lights you bought: four lights, one in each corner, are
;; stuck on and can't be turned off.

;; In your grid of 100x100 lights, given your initial configuration,
;; but with the four corners always in the on state, how many lights
;; are on after 100 steps?

(defn apply-stuck-lights [len lights]
  (reduce #(assoc %1 %2 1) lights [(light-position len 0 0)
                                   (light-position len (dec len) 0)
                                   (light-position len 0 (dec len))
                                   (light-position len (dec len) (dec len))]))

(defn transition-lights-2 [len lights]
  (apply-stuck-lights len (vec (for [y (range len) x (range len)]
                                 (transition-light len lights x y)))))

(defn count-lights-after-steps-2 [lights n]
  (let [len (lights-length lights) sl (apply-stuck-lights len lights)]
    (count-lights-on (nth (iterate (partial transition-lights-2 len) sl) n))))
