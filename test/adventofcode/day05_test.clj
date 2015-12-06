(ns adventofcode.day05-test
  (:require [adventofcode.day05 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; * ugknbfddgicrmopn is nice because it has at least three vowels
;;   (u...i...o...), a double letter (...dd...), and none of the
;;   disallowed substrings.
;; * aaa is nice because it has at least three vowels and a double
;;   letter, even though the letters used by different rules overlap.
;; * jchzalrnumimnmhp is naughty because it has no double letter.
;; * haegwjzuvuyypxyu is naughty because it contains the string xy.
;; * dvszwmarrgswjxmb is naughty because it contains only one vowel.

(deftest test-naughty-or-nice-1
  (is (= :nice (naughty-or-nice "ugknbfddgicrmopn"))))

(deftest test-naughty-or-nice-2
  (is (= :nice (naughty-or-nice "aaa"))))

(deftest test-naughty-or-nice-3
  (is (= :naughty (naughty-or-nice "jchzalrnumimnmhp"))))

(deftest test-naughty-or-nice-4
  (is (= :naughty (naughty-or-nice "haegwjzuvuyypxyu"))))

(deftest test-naughty-or-nice-5
  (is (= :naughty (naughty-or-nice "dvszwmarrgswjxmb"))))

(deftest test-count-nice
  (is (= 2 (count-nice naughty-or-nice
                       ["ugknbfddgicrmopn" "aaa" "jchzalrnumimnmhp"]))))

;; Part 2

;; qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice
;; (qj) and a letter that repeats with exactly one letter between them
;; (zxz).

;; xxyxx is nice because it has a pair that appears twice and a letter
;; that repeats with one between, even though the letters used by each
;; rule overlap.

;; uurcxstgmygtbstg is naughty because it has a pair (tg) but no
;; repeat with a single letter between them.

;; ieodomkazucvgmuy is naughty because it has a repeating letter with
;; one between (odo), but no pair that appears twice.

(deftest test-naughty-or-nice-2-1
  (is (= :nice (naughty-or-nice-2 "qjhvhtzxzqqjkmpb"))))

(deftest test-naughty-or-nice-2-2
  (is (= :nice (naughty-or-nice-2 "xxyxx"))))

(deftest test-naughty-or-nice-2-3
  (is (= :naughty (naughty-or-nice-2 "uurcxstgmygtbstg"))))

(deftest test-naughty-or-nice-2-4
  (is (= :naughty (naughty-or-nice-2 "ieodomkazucvgmuy"))))
