(ns adventofcode.day11
  (:gen-class))

;; Day 11: Corporate Policy

;; Santa's previous password expired, and he needs help choosing a new
;; one.

;; To help him remember his new password after the old one expires,
;; Santa has devised a method of coming up with a password based on
;; the previous one. Corporate policy dictates that passwords must be
;; exactly eight lowercase letters (for security reasons), so he finds
;; his new password by incrementing his old password string repeatedly
;; until it is valid.

;; Incrementing is just like counting with numbers: xx, xy, xz, ya,
;; yb, and so on. Increase the rightmost letter one step; if it was z,
;; it wraps around to a, and repeat with the next letter to the left
;; until one doesn't wrap around.

;; Unfortunately for Santa, a new Security-Elf recently started, and
;; he has imposed some additional password requirements:

;; * Passwords must include one increasing straight of at least three
;;   letters, like abc, bcd, cde, and so on, up to xyz. They cannot
;;   skip letters; abd doesn't count.
;; * Passwords may not contain the letters i, o, or l, as these
;;   letters can be mistaken for other characters and are therefore
;;   confusing.
;; * Passwords must contain at least two different, non-overlapping
;;   pairs of letters, like aa, bb, or zz.

;; Given Santa's current password (your puzzle input), what should his
;; next password be?

(defn password-ok-increasing-straight-three [pass]
  (let [nums (map int pass)]
    (some true? (map #(= (inc (first %)) (second %) (dec (nth % 2)))
                     (partition 3 1 nums)))))

(defn password-ok-no-bad-letters [pass]
  (not (some #{\i \o \l} pass)))

(defn password-ok-two-different-pairs [pass]
  (> (count (set (map second (re-seq #"(\w)\1" pass)))) 1))

(defn password-ok [pass]
  (every? true? ((juxt password-ok-increasing-straight-three
                       password-ok-no-bad-letters
                       password-ok-two-different-pairs)
                 pass)))

(defn inc-password-char-carry [ch]
  (if (= ch \z) \! (char (inc (int ch)))))

(defn next-password-inc [pass]
  (let [rpass (reverse pass)]
    (clojure.string/replace
     (apply str (reverse (rest (reductions #(if (= %1 \!)
                                              (inc-password-char-carry %2)
                                              %2) \! rpass))))
     \! \a)))

(defn next-password-ff [pass]
  (clojure.string/replace
   pass #"([^iol]+)([iol])(\w*)"
   #(apply str (concat (nth % 1)
                       (str (inc-password-char-carry (first (nth % 2))))
                       (apply str (repeat (count (nth % 3)) \a))))))

(defn next-password [pass]
  (if (re-seq #"[iol]" pass) (next-password-ff pass) (next-password-inc pass)))

(defn next-valid-password [pass]
  (let [npass (next-password pass)]
    (first (drop-while #(not (password-ok %)) (iterate next-password npass)))))


;; Part Two

;; Santa's password expired again. What's the next one?
