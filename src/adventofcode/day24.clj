(ns adventofcode.day24
  (:require [clojure.math.combinatorics :as combo])
  (:gen-class))

;; Day 24: It Hangs in the Balance

;; It's Christmas Eve, and Santa is loading up the sleigh for this
;; year's deliveries. However, there's one small problem: he can't get
;; the sleigh to balance. If it isn't balanced, he can't defy physics,
;; and nobody gets presents this year.

;; No pressure.

;; Santa has provided you a list of the weights of every package he
;; needs to fit on the sleigh. The packages need to be split into
;; three groups of exactly the same weight, and every package has to
;; fit. The first group goes in the passenger compartment of the
;; sleigh, and the second and third go in containers on either side.
;; Only when all three groups weigh exactly the same amount will the
;; sleigh be able to fly. Defying physics has rules, you know!

;; Of course, that's not the only problem. The first group - the one
;; going in the passenger compartment - needs as few packages as
;; possible so that Santa has some legroom left over. It doesn't
;; matter how many packages are in either of the other two groups, so
;; long as all of the groups weigh the same.

;; Furthermore, Santa tells you, if there are multiple ways to arrange
;; the packages such that the fewest possible are in the first group,
;; you need to choose the way where the first group has the smallest
;; quantum entanglement to reduce the chance of any "complications".
;; The quantum entanglement of a group of packages is the product of
;; their weights, that is, the value you get when you multiply their
;; weights together. Only consider quantum entanglement if the first
;; group has the fewest possible number of packages in it and all
;; groups weigh the same amount.

;; What is the quantum entanglement of the first group of packages in
;; the ideal configuration?


;; What each container must weigh to balance the sleigh.
(defn sleigh-balance-weight [packages containers]
  (/ (apply + packages) containers))

;; For size packages, return a list of package lists that would
;; balance the sleigh.
(defn find-fc-package-candidates [packages containers size]
  (filter #(= (apply + %) (sleigh-balance-weight packages containers))
          (combo/combinations packages size)))

;; Find the smallest number of packages that would balance the sleigh
;; and return a list of candidate packages.
(defn find-smallest-fc-packages [packages containers]
  (some not-empty (map (partial find-fc-package-candidates packages containers)
                       (range 1 (count packages)))))

;; Find the best quantum entanglement for the above.
(defn find-best-qe-smallest-fc-packages [packages containers]
  (apply min (map #(reduce * 1 %)
                  (find-smallest-fc-packages packages containers))))

;; Part Two

;; That's weird... the sleigh still isn't balancing.

;; "Ho ho ho", Santa muses to himself. "I forgot the trunk".

;; Balance the sleigh again, but this time, separate the packages into
;; four groups instead of three. The other constraints still apply.

;; Now, what is the quantum entanglement of the first group of
;; packages in the ideal configuration?


;; I just refactored the above to take the number of containers as a
;; parameter.
