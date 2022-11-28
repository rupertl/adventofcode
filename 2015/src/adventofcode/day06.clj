(ns adventofcode.day06
  (:gen-class))

;; Day 6: Probably a Fire Hazard

;; Because your neighbors keep defeating you in the holiday house
;; decorating contest year after year, you've decided to deploy one
;; million lights in a 1000x1000 grid.

;; Furthermore, because you've been especially nice this year, Santa
;; has mailed you instructions on how to display the ideal lighting
;; configuration.

;; Lights in your grid are numbered from 0 to 999 in each direction;
;; the lights at each corner are at 0,0, 0,999, 999,999, and 999,0.
;; The instructions include whether to turn on, turn off, or toggle
;; various inclusive ranges given as coordinate pairs. Each coordinate
;; pair represents opposite corners of a rectangle, inclusive; a
;; coordinate pair like 0,0 through 2,2 therefore refers to 9 lights
;; in a 3x3 square. The lights all start turned off.

;; To defeat your neighbors this year, all you have to do is set up
;; your lights by doing the instructions Santa sent you in order.

;; After following the instructions, how many lights are lit?


;; We use a flat vector with each light at x + 1000y to represent the
;; lights.

(defn house-lights []
  "Initial state of house lights"
  (vec (take 1000000 (repeat -1))))

(defn coords-to-points [x1 y1 x2 y2]
  "Convert a set of coordinates to a list of points"
  (for [x (range x1 (inc x2)) y (range y1 (inc y2))] (+ x (* 1000 y))))

(defn do-light-point-instruction [lights point instr]
  "Return the result of applying instr to point in light"
  (update lights point #(cond (= instr :on) 1
                              (= instr :off) -1
                              (= instr :toggle) (* -1 %))))

(defn do-light-coords-instruction [lights x1 y1 x2 y2 instr]
  "Apply instr to a rectangle bounded by x1y1-x2y2 in light"
  (reduce #(do-light-point-instruction %1 %2 instr)
          lights (coords-to-points x1 y1 x2 y2)))

(defn do-list-light-coords-instructions [lights list]
  "Apply a set of instructions to lights"
  (reduce #(let [[x1 y1 x2 y2 instr] %2]
             (do-light-coords-instruction %1 x1 y1 x2 y2 instr))
          lights list))

(defn count-lit [lights]
  "Count how many lights are lit"
  (count (filter #(= % 1) lights)))

(defn house-lights-lit [list]
  (count-lit (do-list-light-coords-instructions (house-lights) list)))

;; Part Two

;; You just finish implementing your winning light pattern when you
;; realize you mistranslated Santa's message from Ancient Nordic
;; Elvish.

;; The light grid you bought actually has individual brightness
;; controls; each light can have a brightness of zero or more. The
;; lights all start at zero.

;; The phrase turn on actually means that you should increase the
;; brightness of those lights by 1.

;; The phrase turn off actually means that you should decrease the
;; brightness of those lights by 1, to a minimum of zero.

;; The phrase toggle actually means that you should increase the
;; brightness of those lights by 2.

;; What is the total brightness of all lights combined after following
;; Santa's instructions?

(defn house-lights-2 []
  "Initial state of house lights"
  (vec (take 1000000 (repeat 0))))

(defn do-light-point-instruction-2 [lights point instr]
  "Return the result of applying instr to point in light"
  (update lights point #(cond (= instr :on) (inc %)
                              (= instr :off) (max (dec %) 0)
                              (= instr :toggle) (+ 2 %))))

(defn do-light-coords-instruction-2 [lights x1 y1 x2 y2 instr]
  "Apply instr to a rectangle bounded by x1y1-x2y2 in light"
  (reduce #(do-light-point-instruction-2 %1 %2 instr)
          lights (coords-to-points x1 y1 x2 y2)))

(defn do-list-light-coords-instructions-2 [lights list]
  "Apply a set of instructions to lights"
  (reduce #(let [[x1 y1 x2 y2 instr] %2]
             (do-light-coords-instruction-2 %1 x1 y1 x2 y2 instr))
          lights list))

(defn total-brightness [lights]
  "Count total brightness for all lights"
  (reduce + lights))

(defn house-lights-lit-2 [list]
  (total-brightness (do-list-light-coords-instructions-2 (house-lights-2) list)))
