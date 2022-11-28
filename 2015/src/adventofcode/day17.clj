(ns adventofcode.day17
  (:require [clojure.math.combinatorics :as combo])
  (:gen-class))

;; Day 17: No Such Thing as Too Much

;; The elves bought too much eggnog again - 150 liters this time. To
;; fit it all into your refrigerator, you'll need to move it into
;; smaller containers. You take an inventory of the capacities of the
;; available containers.

;; Filling all containers entirely, how many different combinations of
;; containers can exactly fit all 150 liters of eggnog?


;; Recursive solution - could be a bit shorted I think.

(defn eggnog-containers [containers litres]
  (if (empty? containers) (list)
      (let [f (first containers) r (rest containers)]
        (concat (cond (= f litres) (list f)
                      (< f litres) (map #(conj (list f) %)
                                        (eggnog-containers r (- litres f)))
                      :else (list))
                (eggnog-containers r litres)))))

(defn how-many-eggnog-containers [containers litres]
  (count (eggnog-containers containers litres)))


;; Part 2

;; While playing with all the containers in the kitchen, another load
;; of eggnog arrives! The shipping and receiving department is
;; requesting as many containers as you can spare.

;; Find the minimum number of containers that can exactly fit all 150
;; liters of eggnog. How many different ways can you fill that number
;; of containers and still hold exactly 150 litres?

;; In the example above, the minimum number of containers was two.
;; There were three ways to use that many containers, and so the
;; answer there would be 3.


;; Put the number of containers and their counts into a sorted map

(defn how-many-min-eggnog-containers [containers litres]
  (val (first (reduce #(assoc %1 (count %2) (inc (or (get %1 (count %2)) 0)))
                      (sorted-map)
                      (map flatten (eggnog-containers containers litres))))))
