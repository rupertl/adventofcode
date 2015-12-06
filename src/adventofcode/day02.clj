(ns adventofcode.day02
  (:gen-class))

;; Day 2: I Was Told There Would Be No Math

;; Part 1

;; The elves are running low on wrapping paper, and so they need to
;; submit an order for more. They have a list of the dimensions
;; (length l, width w, and height h) of each present, and only want to
;; order exactly as much as they need.

;; Fortunately, every present is a box (a perfect right rectangular
;; prism), which makes calculating the required wrapping paper for
;; each gift a little easier: find the surface area of the box, which
;; is 2*l*w + 2*w*h + 2*h*l. The elves also need a little extra paper
;; for each present: the area of the smallest side.

;; All numbers in the elves' list are in feet. How many total square feet of
;; wrapping paper should they order?

;; RL note: input is formulated to look like:
;; (def input-02 [[20 3 11] [15 27 5]])

(defn paper-required [l w h]
  (let [a1 (* l w) a2 (* w h) a3 (* h l)]
    (+ (* 2 (+ a1 a2 a3))
       (min a1 a2 a3))))

(defn total-paper-required [list]
  (reduce + (map #(apply paper-required %1) list)))

;; Part 2

;; The elves are also running low on ribbon. Ribbon is all the same
;; width, so they only have to worry about the length they need to order,
;; which they would again like to be exact.

;; The ribbon required to wrap a present is the shortest distance around
;; its sides, or the smallest perimeter of any one face. Each present
;; also requires a bow made out of ribbon as well; the feet of ribbon
;; required for the perfect bow is equal to the cubic feet of volume of
;; the present. Don't ask how they tie the bow, though; they'll never
;; tell.

;; How many total feet of ribbon should they order?

(defn ribbon-required [dims]
  (let [ss (take 2 (sort dims)) vol (reduce * 1 dims)]
    (+ (* 2 (reduce + ss)) vol)))

(defn total-ribbon-required [list]
  (reduce + (map ribbon-required list)))
