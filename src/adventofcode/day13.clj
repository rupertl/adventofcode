(ns adventofcode.day13
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set])
  (:gen-class))

;; Day 13: Knights of the Dinner Table

;; In years past, the holiday feast with your family hasn't gone so
;; well. Not everyone gets along! This year, you resolve, will be
;; different. You're going to find the optimal seating arrangement and
;; avoid all those awkward conversations.

;; You start by writing up a list of everyone invited and the amount
;; their happiness would increase or decrease if they were to find
;; themselves sitting next to each other person. You have a circular
;; table that will be just big enough to fit everyone comfortably, and
;; so each person will have exactly two neighbors.

;; What is the total change in happiness for the optimal seating
;; arrangement of the actual guest list?


;; Another brute force solution using permutations.

(defn dinner-names [happiness]
  (set (flatten (keys happiness))))

(defn net-happiness [happiness couple]
  (+ (happiness couple) (happiness (reverse couple))))

(defn total-happiness [happiness names]
  (reduce + (map (partial net-happiness happiness)
                 (partition 2 1 (conj names (first names))))))

(defn best-total-happiness [happiness]
  (apply max (map #(total-happiness happiness (vec %))
                  (combo/permutations (dinner-names happiness)))))


;; Part Two

;; In all the commotion, you realize that you forgot to seat yourself.
;; At this point, you're pretty apathetic toward the whole thing, and
;; your happiness wouldn't really go up or down regardless of who you
;; sit next to. You assume everyone else would be just as ambivalent
;; about sitting next to you, too.

;; So, add yourself to the list, and give all happiness relationships
;; that involve you a score of 0.

;; What is the total change in happiness for the optimal seating
;; arrangement that actually includes yourself?

(defn add-me-to-dinner [happiness]
  (reduce #(assoc %1 [:me %2] 0 [%2 :me] 0) happiness (dinner-names happiness)))
