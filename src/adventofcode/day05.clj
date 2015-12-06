(ns adventofcode.day05
  (:gen-class))

;; Day 5: Doesn't He Have Intern-Elves For This?

;; Santa needs help figuring out which strings in his text file are
;; naughty or nice.

;; A nice string is one with all of the following properties:

;; * It contains at least three vowels (aeiou only), like aei,
;;   xazegov, or aeiouaeiouaeiou.
;; * It contains at least one letter that appears twice in a row, like
;;   xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
;; * It does not contain the strings ab, cd, pq, or xy, even if they
;;   are part of one of the other requirements.

;; How many strings are nice?

(defn naughty-or-nice [str]
  (if (and (>= (count (re-seq #"[aeiou]" str)) 3)
           (re-find #"(\w)\1+" str)
           (not (re-find #"(ab)|(cd)|(pq)|(xy)" str)))
    :nice :naughty))

(defn count-nice [fn words]
  (count (filter #(= % :nice) (map fn words))))

;; Realizing the error of his ways, Santa has switched to a better
;; model of determining whether a string is naughty or nice. None of
;; the old rules apply, as they are all clearly ridiculous.

;; Now, a nice string is one with all of the following properties:

;;     It contains a pair of any two letters that appears at least
;;     twice in the string without overlapping, like xyxy (xy) or
;;     aabcdefgaa (aa), but not like aaa (aa, but it overlaps).

;;     It contains at least one letter which repeats with exactly one
;;     letter between them, like xyx, abcdefeghi (efe), or even aaa.

;; How many strings are nice under these new rules?

(defn naughty-or-nice-2 [str]
  (if (and (re-find #"(\w\w).*\1+" str)
           (re-find #"(\w).\1+" str))
    :nice :naughty))
