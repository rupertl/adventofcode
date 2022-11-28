(ns adventofcode.day16-test
  (:require [adventofcode.day16 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

(def test16-sues
  {
   1 {:goldfish 9, :perfume 0, :samoyeds 9}
   2 {:pomeranians 2, :akitas 1, :perfume 9}
   3 {:perfumes 5, :trees 8, :goldfish 8}
   4 {:goldfish 10, :akitas 1, :perfumes 9}
   5 {:cars 5, :perfumes 6, :akitas 9}
   })

(def test16-facts {:akitas 1 :perfume 9 :goldfish 9})

(deftest test-which-sue
  (is (= 2 (which-sue-matches-1 test16-sues test16-facts))))

(def test16-sues-2
  {
   1 {:goldfish 9, :perfume 0, :samoyeds 9}
   2 {:pomeranians 2, :goldfish 9, :perfume 9}
   3 {:pomeranians 2, :goldfish 6, :perfume 9}
   4 {:perfumes 5, :trees 8, :akitas 3, :goldfish 8}
   5 {:goldfish 10, :akitas 1, :perfumes 9}
   6 {:cars 5, :perfumes 6, :akitas 9}
   })

(deftest test-which-sue-2
  (is (= 3 (which-sue-matches-2 test16-sues-2 test16-facts))))
