(ns adventofcode.day08
  (:gen-class))

;; Day 8: Matchsticks

;; Space on the sleigh is limited this year, and so Santa will be
;; bringing his list as a digital copy. He needs to know how much
;; space it will take up when stored.

;; It is common in many programming languages to provide a way to
;; escape special characters in strings. For example, C, JavaScript,
;; Perl, Python, and even PHP handle special characters in very
;; similar ways.

;; However, it is important to realize the difference between the
;; number of characters in the code representation of the string
;; literal and the number of characters in the in-memory string
;; itself.

;; Santa's list is a file that contains many double-quoted string
;; literals, one on each line. The only escape sequences used are \\
;; (which represents a single backslash), \" (which represents a lone
;; double-quote character), and \x plus two hexadecimal characters
;; (which represents a single character with that ASCII code).

;; Disregarding the whitespace in the file, what is the number of
;; characters of code for string literals minus the number of
;; characters in memory for the values of the strings in total for the
;; entire file?


;; ----------------------------------------

;; Note that Clojure does not have raw strings or support \x natively.

;; So here's a very silly implementation - we take input as regexps,
;; so "a\\b" would be input as #"a\\b", and replace these with unicode
;; markers to get the 'coded' version. We then replace them with a
;; stand in character or strip these off to get the 'memory' version.

;; † - starting double quote
;; ①① - \\
;; ②② - \"
;; ③③ - \x
;; ‡ - closing double quote

(defn regexp-to-code [re]
  (str "†"
       (clojure.string/replace
        (clojure.string/replace
         (clojure.string/replace (str re)
                                 #"\\\\" "①①") #"\\\"" "②②") #"\\x" "③③")
       "‡"))

(defn code-to-memory [code]
  (clojure.string/replace
   (clojure.string/replace code #"①①|②②|③③.." "❄" )
   #"†|‡" ""))

(defn code-memory-difference [re]
  (let [code (regexp-to-code re)]
    (- (count code) (count (code-to-memory code)))))

(defn total-code-memory-difference [res]
  (reduce + (map code-memory-difference res)))

;; Part Two

;; Now, let's go the other way. In addition to finding the number of
;; characters of code, you should now encode each code representation
;; as a new string and find the number of characters of the new
;; encoded representation, including the surrounding double quotes.

;; Your task is to find the total number of characters to represent
;; the newly encoded strings minus the number of characters of code in
;; each original string literal.

(defn code-to-encoded [code]
  (clojure.string/replace
   (clojure.string/replace
    (clojure.string/replace code
                            #"①①|②②" "❄❄❄❄" ) #"③③" "❄❄❄") #"†|‡" "❄❄❄"))

(defn encoded-code-difference [re]
  (let [code (regexp-to-code re)]
    (- (count (code-to-encoded code)) (count code) )))

(defn total-encoded-code-difference [res]
  (reduce + (map encoded-code-difference res)))
