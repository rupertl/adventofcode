(ns adventofcode.day12
  (:require [clojure.data.json :as json])
  (:gen-class))

;; Day 12: JSAbacusFramework.io

;; Santa's Accounting-Elves need help balancing the books after a
;; recent order. Unfortunately, their accounting software uses a
;; peculiar storage format. That's where you come in.

;; They have a JSON document which contains a variety of things:
;; arrays ([1,2,3]), objects ({"a":1, "b":2}), numbers, and strings.
;; Your first job is to simply find all of the numbers throughout the
;; document and add them together.

;; You will not encounter any strings containing numbers.

;; What is the sum of all numbers in the document?


;; So we can just use a regexp here

(defn accounting-sum [data]
  (apply + (map read-string (re-seq #"-*\d+" data))))

;; Part Two

;; Uh oh - the Accounting-Elves have realized that they double-counted
;; everything red.

;; Ignore any object (and all of its children) which has any property
;; with the value "red". Do this only for objects ({...}), not arrays
;; ([...]).


;; But now we need to parse the JSON, walk the form and remove maps
;; where "red" is one of the vals.

(defn remove-red [form]
  (if (and (map? form) (some #(= % "red") (vals form))) 0 form))

(defn walk-form-remove-red [form]
  (clojure.walk/prewalk remove-red form))

(defn accounting-sum-no-red [data]
  (accounting-sum (json/write-str (walk-form-remove-red (json/read-str data)))))
