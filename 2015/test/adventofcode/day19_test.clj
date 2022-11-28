(ns adventofcode.day19-test
  (:require [adventofcode.day19 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, imagine a simpler machine that supports only the
;; following replacements:

;; H => HO
;; H => OH
;; O => HH

;; Given the replacements above and starting with HOH, the following
;; molecules could be generated:

;;     HOOH (via H => HO on the first H).
;;     HOHO (via H => HO on the second H).
;;     OHOH (via H => OH on the first H).
;;     HOOH (via H => OH on the second H).
;;     HHHH (via O => HH).

;; So, in the example above, there are 4 distinct molecules (not five,
;; because HOOH appears twice) after one replacement from HOH. Santa's
;; favorite molecule, HOHOHO, can become 7 distinct molecules (over
;; nine replacements: six from H, and three from O).

(def test19 {:replacements [["H" "HO"] ["H" "OH"] ["O" "HH"]]
             :compound "HOH"})

(deftest test-rnrffm-hoh-compounds
  (is (= 4 (calibrate-rnrffm-count test19 ))))

(deftest test-rnrffm-hohoho-compounds
  (is (= 7 (calibrate-rnrffm-count (assoc test19 :compound "HOHOHO") ))))

;; Part Two

;; For example, suppose you have the following replacements:

;; e => H
;; e => O
;; H => HO
;; H => OH
;; O => HH

;; If you'd like to make HOH, you start with e, and then make the
;; following replacements:

;;     e => O to get O
;;     O => HH to get HH
;;     H => OH (on the second H) to get HOH

;; So, you could make HOH after 3 steps. Santa's favorite molecule,
;; HOHOHO, can be made in 6 steps.

(def test19-2
  {:replacements [["e" "H"] ["e" "O"] ["H" "HO"] ["H" "OH"] ["O" "HH"]]
   :compound "HOH"})

(deftest test-rnrffm-hoh-steps
  (is (= 3 (fabricate-steps test19-2))))

(deftest test-rnrffm-hohoho-steps
  (is (= 6 (fabricate-steps (assoc test19-2 :compound "HOHOHO") ))))

(deftest test-rnrffm-hoh-steps-defabricate
  (is (= 3 (defabricate-steps test19-2))))

(deftest test-rnrffm-hohoho-steps-defabricate
  (is (= 6 (fabricate-steps (assoc test19-2 :compound "HOHOHO") ))))
