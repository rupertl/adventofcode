(ns adventofcode.day09-test
  (:require [adventofcode.day09 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, given the following distances:

;; London to Dublin = 464
;; London to Belfast = 518
;; Dublin to Belfast = 141

;; The possible routes are therefore:

;; Dublin -> London -> Belfast = 982
;; London -> Dublin -> Belfast = 605
;; London -> Belfast -> Dublin = 659
;; Dublin -> Belfast -> London = 659
;; Belfast -> Dublin -> London = 605
;; Belfast -> London -> Dublin = 982

;; The shortest of these is London -> Dublin -> Belfast = 605, and so
;; the answer is 605 in this example.

(def test9
  { #{:London :Dublin} 464 #{:London  :Belfast} 518 #{:Dublin  :Belfast} 141})

(deftest test-get-cities
  (is (= #{:London :Dublin :Belfast} (get-cities test9))))

(deftest test-city-distance
  (is (= 518 (city-distance test9 :Belfast :London ))))

(deftest test-trip-distance
  (is (= 982 (trip-distance test9 [:Dublin :London :Belfast]))))

(deftest test-min-trip-distance
  (is (= 605 (min-trip-distance test9))))

;; Part 2

;; For example, given the distances above, the longest route would be
;; 982 via (for example) Dublin -> London -> Belfast.

(deftest test-max-trip-distance
  (is (= 982 (max-trip-distance test9))))
