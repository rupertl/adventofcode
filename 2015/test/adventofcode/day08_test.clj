(ns adventofcode.day08-test
  (:require [adventofcode.day08 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example:

;; * "" is 2 characters of code (the two double quotes), but the string contains
;;   zero characters.
;; * "abc" is 5 characters of code, but 3 characters in the string data.
;; * "aaa\"aaa" is 10 characters of code, but the string itself contains six "a"
;;   characters and a single, escaped quote character, for a total of 7 characters
;;   in the string data.
;; * "\x27" is 6 characters of code, but the string itself contains just one - an
;;   apostrophe ('), escaped using hexadecimal notation.

;; For example, given the four strings above, the total number of
;; characters of string code (2 + 5 + 10 + 6 = 23) minus the total
;; number of characters in memory for string values (0 + 3 + 7 + 1 =
;; 11) is 23 - 11 = 12.

(deftest test-matchstick-1
  (is (= 2 (code-memory-difference #""))))

(deftest test-matchstick-2
  (is (= 2 (code-memory-difference #"abc"))))

(deftest test-matchstick-3
  (is (= 3 (code-memory-difference #"aaa\"aaa"))))

(deftest test-matchstick-4
  (is (= 5 (code-memory-difference #"\x27"))))

(deftest test-matchstick-all
  (is (= 12 (total-code-memory-difference [#"" #"abc" #"aaa\"aaa" #"\x27"]))))


;; Part Two

;; For example:

;;     "" encodes to "\"\"", an increase from 2 characters to 6.
;;     "abc" encodes to "\"abc\"", an increase from 5 characters to 9.
;;     "aaa\"aaa" encodes to "\"aaa\\\"aaa\"", an increase from 10
;;     characters to 16.
;;     "\x27" encodes to "\"\\x27\"", an increase from 6 characters to 11.

;; For example, for the strings above, the total encoded length (6 + 9
;; + 16 + 11 = 42) minus the characters in the original code
;; representation (23, just like in the first part of this puzzle) is
;; 42 - 23 = 19.

(deftest test-matchstick-encoded-1
  (is (= 4 (encoded-code-difference #""))))

(deftest test-matchstick-encoded-2
  (is (= 4 (encoded-code-difference #"abc"))))

(deftest test-matchstick-encoded-3
  (is (= 6 (encoded-code-difference #"aaa\"aaa"))))

(deftest test-matchstick-encoded-4
  (is (= 5 (encoded-code-difference #"\x27"))))

(deftest test-matchstick-encoded-all
  (is (= 19 (total-encoded-code-difference [#"" #"abc" #"aaa\"aaa" #"\x27"]))))
