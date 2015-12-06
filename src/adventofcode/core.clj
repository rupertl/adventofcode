(ns adventofcode.core
  (:require [adventofcode.day01 :refer :all])
  (:gen-class))

;; Load the test inputs as input-01, input-02 etc
;; These will be unique for each player
(load-file "resources/inputs.clj")

(defn -main
  "Runs each day's problems with test inputs"
  [& args]
  (println "Day  1 Part 1: " (which-floor input-01))
  (println "Day  1 Part 2: " (basement-instruction input-01))
  )
