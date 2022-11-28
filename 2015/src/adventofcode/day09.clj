(ns adventofcode.day09
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set])
  (:gen-class))

;; Day 9: All in a Single Night

;; Every year, Santa manages to deliver all of his presents in a
;; single night.

;; This year, however, he has some new locations to visit; his elves
;; have provided him the distances between every pair of locations. He
;; can start and end at any two (different) locations he wants, but he
;; must visit each location exactly once. What is the shortest
;; distance he can travel to achieve this?

;; What is the distance of the shortest route?


;; Ah, the travelling Santa problem!

;; We formulate input as { #{:London :Dublin} 464 #{:London :Belfast} }
;; etc. trip-distance is a recursive function to find the distance
;; between a seq of cities. To get the min, we just try all the
;; permutations.

(defn get-cities [distances]
  (apply set/union (keys distances)))

(defn city-distance [distances from to]
  (distances #{from to}))

(defn trip-distance [distances cities]
  (let [cd (city-distance distances (first cities) (second cities))]
    (if (= (count cities) 2)
      cd
      (+ cd (trip-distance distances (rest cities))))))

(defn min-trip-distance [distances]
  (apply min (map (partial trip-distance distances)
                  (combo/permutations (get-cities distances)))))

;; Part 2

;; The next year, just to show off, Santa decides to take the route
;; with the longest distance instead.

;; He can still start and end at any two (different) locations he
;; wants, and he still must visit each location exactly once.

;; What is the distance of the longest route?

(defn max-trip-distance [distances]
  (apply max (map (partial trip-distance distances)
                  (combo/permutations (get-cities distances)))))
