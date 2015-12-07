(ns adventofcode.day07
  (:gen-class))

; ;Day 7: Some Assembly Required

;; This year, Santa brought little Bobby Tables a set of wires and
;; bitwise logic gates! Unfortunately, little Bobby is a little under
;; the recommended age range, and he needs help assembling the
;; circuit.

;; Each wire has an identifier (some lowercase letters) and can carry
;; a 16-bit signal (a number from 0 to 65535). A signal is provided to
;; each wire by a gate, another wire, or some specific value. Each
;; wire can only get a signal from one source, but can provide its
;; signal to multiple destinations. A gate provides no signal until
;; all of its inputs have a signal.

;; The included instructions booklet describe how to connect the parts
;; together: x AND y -> z means to connect wires x and y to an AND
;; gate, and then connect its output to wire z.

;; For example:

;; * 123 -> x means that the signal 123 is provided to wire x.
;; * x AND y -> z means that the bitwise AND of wire x and wire y is
;;   provided to wire z.
;; * p LSHIFT 2 -> q means that the value from wire p is left-shifted
;;   by 2 and then provided to wire q.
;; * NOT e -> f means that the bitwise complement of the value from wire e is
;;   provided to wire f.

;; Other possible gates include OR (bitwise OR) and RSHIFT
;; (right-shift). If, for some reason, you'd like to emulate the
;; circuit instead, almost all programming languages (for example, C,
;; JavaScript, or Python) provide operators for these gates.

;; In little Bobby's kit's instructions booklet (provided as your
;; puzzle input), what signal is ultimately provided to wire a?

;; This runs too slowly so need to memoize
(declare wire-value)

(defn real-wire-value [circuit wire]
  (let [val (get circuit wire)]
    (cond (number? wire) wire
          (number? (first val)) (first val)
          (= (count val) 1) (wire-value circuit (first val))
          :else ((first val) circuit (rest val)))))

(def wire-value (memoize real-wire-value))

(defn mask-16bits [val]
  (bit-and val 2r1111111111111111))

(defn AND [circuit vals]
  (bit-and (wire-value circuit (first vals)) (wire-value circuit (second vals))))

(defn OR [circuit vals]
  (bit-or (wire-value circuit (first vals)) (wire-value circuit (second vals))))

(defn NOT [circuit vals]
  (mask-16bits (bit-not (wire-value circuit (first vals)))))

(defn LSHIFT [circuit vals]
  (mask-16bits (bit-shift-left (wire-value circuit (first vals)) (second vals))))

(defn RSHIFT [circuit vals]
  (bit-shift-right (wire-value circuit (first vals)) (second vals)))

;; Now, take the signal you got on wire a, override wire b to that
;; signal, and reset the other wires (including wire a). What new
;; signal is ultimately provided to wire a?

(defn override-wire-b [circuit wire]
  (let [first-result (wire-value circuit wire)]
    (wire-value (assoc circuit :b [first-result]) wire)))
