(ns adventofcode.day14-test
  (:require [adventofcode.day14 :refer :all]
            [clojure.test :refer :all]
            [adventofcode.core :refer :all]))

;; For example, suppose you have the following Reindeer:

;;     Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
;;     Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.

;; After one second, Comet has gone 14 km, while Dancer has gone 16
;; km. After ten seconds, Comet has gone 140 km, while Dancer has gone
;; 160 km. On the eleventh second, Comet begins resting (staying at
;; 140 km), and Dancer continues on for a total distance of 176 km. On
;; the 12th second, both reindeer are resting. They continue to rest
;; until the 138th second, when Comet flies for another ten seconds.
;; On the 174th second, Dancer flies for another 11 seconds.

;; In this example, after the 1000th second, both reindeer are
;; resting, and Comet is in the lead at 1120 km (poor Dancer has only
;; gotten 1056 km by that point). So, in this situation, Comet would
;; win (if the race ended at 1000 seconds).

(def test14
  [
   {:name :Comet :speed 14 :fly-time 10 :rest-time 127}
   {:name :Dancer :speed 16 :fly-time 11 :rest-time 162}
   ])

(deftest test-dist-comet-1
  (is (= 14 (reindeer-distance (first test14) 1))))

(deftest test-dist-dancer-1
  (is (= 16  (reindeer-distance (second test14) 1))))

(deftest test-dist-comet-10
  (is (= 140 (reindeer-distance (first test14) 10))))

(deftest test-dist-dancer-10
  (is (= 160 (reindeer-distance (second test14) 10))))

(deftest test-dist-comet-11
  (is (= 140 (reindeer-distance (first test14) 11))))

(deftest test-dist-dancer-11
  (is (= 176 (reindeer-distance (second test14) 11))))

(deftest test-dist-comet-1000
  (is (= 1120 (reindeer-distance (first test14) 1000))))

(deftest test-dist-dancer-1000
  (is (= 1056 (reindeer-distance (second test14) 1000))))

(deftest test-max-reindeer-distance
  (is (= 1120 (max-reindeer-distance test14 1000))))

;; Part 2

;; Seeing how reindeer move in bursts, Santa decides he's not pleased
;; with the old scoring system.

;; Given the example reindeer from above, after the first second,
;; Dancer is in the lead and gets one point. He stays in the lead
;; until several seconds into Comet's second burst: after the 140th
;; second, Comet pulls into the lead and gets his first point. Of
;; course, since Dancer had been in the lead for the 139 seconds
;; before that, he has accumulated 139 points by the 140th second.

;; After the 1000th second, Dancer has accumulated 689 points, while
;; poor Comet, our old champion, only has 312. So, with the new
;; scoring system, Dancer would win (if the race ended at 1000
;; seconds).

(deftest test-points-comet-1
  (is (= 0 (first (reindeer-score test14 1)))))

(deftest test-points-dancer-1
  (is (= 1 (second (reindeer-score test14 1)))))

(deftest test-points-comet-140
  (is (= 1 (first (reindeer-score test14 140)))))

(deftest test-points-dancer-140
  (is (= 139 (second (reindeer-score test14 140)))))

(deftest test-points-comet-1000
  (is (= 312 (first (reindeer-score test14 1000)))))

(deftest test-points-dancer-1000
  (is (= 689 (second (reindeer-score test14 1000)))))

(deftest test-max-reindeer-points
  (is (= 689 (max-reindeer-score test14 1000))))
