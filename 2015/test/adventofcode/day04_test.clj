(ns adventofcode.day04-test
  (:require [adventofcode.day04 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; - If your secret key is abcdef, the answer is 609043, because the MD5 hash of
;;   abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest
;;   such number to do so.
;; - If your secret key is pqrstuv, the lowest number it combines with to make an
;;   MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of
;;   pqrstuv1048970 looks like 000006136ef....

(deftest test-first-adventcoin-1
  (is (= 609043 (first-adventcoin "abcdef"))))

(deftest test-first-adventcoin-2
  (is (= 1048970 (first-adventcoin "pqrstuv"))))
