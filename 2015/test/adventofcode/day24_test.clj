(ns adventofcode.day24-test
  (:require [adventofcode.day24 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, suppose you have ten packages with weights 1 through 5
;; and 7 through 11. For this situation, the unique first groups,
;; their quantum entanglements, and a way to divide the remaining
;; packages are as follows:

;; Group 1;             Group 2; Group 3
;; 11 9       (QE= 99); 10 8 2;  7 5 4 3 1
;; 10 9 1     (QE= 90); 11 7 2;  8 5 4 3
;; 10 8 2     (QE=160); 11 9;    7 5 4 3 1
;; 10 7 3     (QE=210); 11 9;    8 5 4 2 1
;; 10 5 4 1   (QE=200); 11 9;    8 7 3 2
;; 10 5 3 2   (QE=300); 11 9;    8 7 4 1
;; 10 4 3 2 1 (QE=240); 11 9;    8 7 5
;; 9 8 3      (QE=216); 11 7 2;  10 5 4 1
;; 9 7 4      (QE=252); 11 8 1;  10 5 3 2
;; 9 5 4 2    (QE=360); 11 8 1;  10 7 3
;; 8 7 5      (QE=280); 11 9;    10 4 3 2 1
;; 8 5 4 3    (QE=480); 11 9;    10 7 2 1
;; 7 5 4 3 1  (QE=420); 11 9;    10 8 2

;; Of these, although 10 9 1 has the smallest quantum entanglement
;; (90), the configuration with only two packages, 11 9, in the
;; passenger compartment gives Santa the most legroom and wins. In
;; this situation, the quantum entanglement for the ideal
;; configuration is therefore 99. Had there been two configurations
;; with only two packages in the first group, the one with the smaller
;; quantum entanglement would be chosen.

(def test-packages (concat (range 1 6) (range 7 12)))

(deftest test-packages-1
  (is 99 (find-best-qe-smallest-fc-packages test-packages 3)))


;; Part Two

;; That's weird... the sleigh still isn't balancing.

;; "Ho ho ho", Santa muses to himself. "I forgot the trunk".

;; Balance the sleigh again, but this time, separate the packages into
;; four groups instead of three. The other constraints still apply.

;; Given the example packages above, this would be the new unique
;; first groups, their quantum entanglements, and one way to divide
;; the remaining packages:

;; 11 4    (QE=44); 10 5;   9 3 2 1; 8 7
;; 10 5    (QE=50); 11 4;   9 3 2 1; 8 7
;; 9 5 1   (QE=45); 11 4;   10 3 2;  8 7
;; 9 4 2   (QE=72); 11 3 1; 10 5;    8 7
;; 9 3 2 1 (QE=54); 11 4;   10 5;    8 7
;; 8 7     (QE=56); 11 4;   10 5;    9 3 2 1

;; Of these, there are three arrangements that put the minimum (two)
;; number of packages in the first group: 11 4, 10 5, and 8 7. Of
;; these, 11 4 has the lowest quantum entanglement, and so it is
;; selected.

;; Now, what is the quantum entanglement of the first group of
;; packages in the ideal configuration?

(deftest test-packages-2
  (is 44 (find-best-qe-smallest-fc-packages test-packages 4)))
