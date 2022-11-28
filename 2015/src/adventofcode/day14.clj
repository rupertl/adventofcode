(ns adventofcode.day14
  (:gen-class))


;; Day 14: Reindeer Olympics

;; This year is the Reindeer Olympics! Reindeer can fly at high
;; speeds, but must rest occasionally to recover their energy. Santa
;; would like to know which of his reindeer is fastest, and so he has
;; them race.

;; Reindeer can only either be flying (always at their top speed) or
;; resting (not moving at all), and always spend whole seconds in
;; either state.

;; Given the descriptions of each reindeer (in your puzzle input),
;; after exactly 2503 seconds, what distance has the winning reindeer
;; traveled?


;; Simple arithmetic to find distance for number of bursts in the
;; time, plus any remainder.

(defn reindeer-distance [{:keys [speed fly-time rest-time]} time]
  (let [burst-dist (* speed fly-time) burst-time (+ fly-time rest-time)]
    (+ (* burst-dist (quot time burst-time))
       (* speed (min (rem time burst-time) fly-time)))))

(defn max-reindeer-distance [reindeer time]
  (apply max (map #(reindeer-distance % time) reindeer)))


;; Part 2

;; Seeing how reindeer move in bursts, Santa decides he's not pleased
;; with the old scoring system.

;; Instead, at the end of each second, he awards one point to the
;; reindeer currently in the lead. (If there are multiple reindeer
;; tied for the lead, they each get one point.) He keeps the
;; traditional 2503 second time limit, of course, as doing otherwise
;; would be entirely ridiculous.

;; Given the example reindeer from above, after the first second,
;; Dancer is in the lead and gets one point. He stays in the lead
;; until several seconds into Comet's second burst: after the 140th
;; second, Comet pulls into the lead and gets his first point. Of
;; course, since Dancer had been in the lead for the 139 seconds
;; before that, he has accumulated 139 points by the 140th second.

;; After the 1000th second, Dancer has accumulated 689 points, while
;; poor Comet, our old champion, only has 312. So, with the new
;; scoring system, Dancer would win (if the race ended at 1000
;; seconds).

;; Again given the descriptions of each reindeer (in your puzzle
;; input), after exactly 2503 seconds, how many points does the
;; winning reindeer have?


;; Recursive solution to find who gets points at this time then add
;; all times down to zero. Needs a stack size increase to run for the
;; puzzle input so there's probably a better way to do this using
;; recur.

(defn reindeer-score-at-time [reindeer time]
  (let [dists (map #(reindeer-distance % time) reindeer)
        best (apply max dists)
        points (map #(if (= best %) 1 0) dists)]
    points))

(defn reindeer-score [reindeer time]
  (reduce #(map + %1 %2)
          (map #(reindeer-score-at-time reindeer %) (range 1 (inc time)))))

(defn max-reindeer-score [reindeer time]
  (apply max (reindeer-score reindeer time)))
