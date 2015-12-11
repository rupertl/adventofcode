(ns adventofcode.day11-test
  (:require [adventofcode.day11 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example:

;; * hijklmmn meets the first requirement (because it contains the
;;   straight hij) but fails the second requirement requirement
;;   (because it contains i and l).

;; * abbceffg meets the third requirement (because it repeats bb and
;;   ff) but fails the first requirement.

;; * abbcegjk fails the third requirement, because it only has one
;;   double letter (bb).

;; * The next password after abcdefgh is abcdffaa.

;; * The next password after ghijklmn is ghjaabcc, because you
;;   eventually skip all the passwords that start with ghi..., since i
;;   is not allowed.

(deftest test-password-ok-1
  (is (= false (password-ok "hijklmmn"))))

(deftest test-password-ok-2
  (is (not (password-ok "abbceffg"))))

(deftest test-password-ok-3
  (is (not (password-ok "abbcegjk"))))

(deftest test-password-ok-4
  (is (not (password-ok "aaabcegjk"))))

(deftest test-password-ok-5
  (is (password-ok "abcdffaa")))

(deftest test-next-password-ok-1
  (is (= "abcdffaa" (next-valid-password "abcdefgh"))))

(deftest test-next-password-ok-2
  (is (= "ghjaabcc" (next-valid-password "ghijklmn"))))
