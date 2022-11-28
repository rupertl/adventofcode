(ns adventofcode.day04
  (:gen-class))

;; Santa needs help mining some AdventCoins (very similar to bitcoins)
;; to use as gifts for all the economically forward-thinking little
;; girls and boys.

;; To do this, he needs to find MD5 hashes which, in hexadecimal,
;; start with at least five zeroes. The input to the MD5 hash is some
;; secret key (your puzzle input, given below) followed by a number in
;; decimal. To mine AdventCoins, you must find Santa the lowest
;; positive number (no leading zeroes: 1, 2, 3, ...) that produces
;; such a hash.

;; -------------------------------
;; md5 implementation from https://gist.github.com/jizhang/4325757
(import 'java.security.MessageDigest
        'java.math.BigInteger)

(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))
;; -------------------------------

(defn adventcoin? [key n]
  (= (subs (md5 (str key n)) 0 5) "00000"))

(defn first-adventcoin [key]
  (some #(if (adventcoin? key %) %) (range)))

;; Now find one that starts with six zeroes.

(defn adventcoin-n? [key n val]
  (= (subs (md5 (str key val)) 0 n) (clojure.string/join (repeat n 0))))

(defn first-adventcoin-n [key n]
  (some #(if (adventcoin-n? key n %) %) (range)))
