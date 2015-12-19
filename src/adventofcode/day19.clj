(ns adventofcode.day19
  (:gen-class))

;; Day 19: Medicine for Rudolph

;; Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very
;; brightly, and he needs medicine.

;; Red-Nosed Reindeer biology isn't similar to regular reindeer
;; biology; Rudolph is going to need custom-made medicine.
;; Unfortunately, Red-Nosed Reindeer chemistry isn't similar to
;; regular reindeer chemistry, either.

;; The North Pole is equipped with a Red-Nosed Reindeer nuclear
;; fusion/fission plant, capable of constructing any Red-Nosed
;; Reindeer molecule you need. It works by starting with some input
;; molecule and then doing a series of replacements, one per step,
;; until it has the right molecule.

;; However, the machine has to be calibrated before it can be used.
;; Calibration involves determining the number of molecules that can
;; be generated in one step from a given starting point.

;; The machine replaces without regard for the surrounding characters.
;; For example, given the string H2O, the transition H => OO would
;; result in OO2O.

;; Your puzzle input describes all of the possible replacements and,
;; at the bottom, the medicine molecule for which you need to
;; calibrate the machine. How many distinct molecules can be created
;; after all the different ways you can do one replacement on the
;; medicine molecule?

(defn molecule-matches [compound molecule]
  (take-while #(>= % 0) (distinct (map #(.indexOf compound molecule %)
                                       (range (count compound))))))

(defn generate-compound [compound molecule replacement index]
  (apply str (concat (subs compound 0 index)
                     replacement
                     (subs compound (+ (count molecule) index)))))

(defn generate-compounds [compound molecule replacement]
  (map (partial generate-compound compound molecule replacement)
       (molecule-matches compound molecule)))

(defn fabricate-compounds [replacements compound]
  (set (mapcat #(apply generate-compounds compound %) replacements)))

(defn calibrate-rnrffm-count [{:keys [compound replacements]}]
  (count (fabricate-compounds replacements compound)))

;; Part Two

;; Now that the machine is calibrated, you're ready to begin molecule
;; fabrication.

;; Molecule fabrication always begins with just a single electron, e,
;; and applying replacements one at a time, just like the ones during
;; calibration.

;; How long will it take to make the medicine? Given the available
;; replacements and the medicine molecule in your puzzle input, what
;; is the fewest number of steps to go from e to the medicine
;; molecule?


;; Here's the obvious way, but it takes too long for the puzzle input.

(defn fabricate-all-compounds [replacements compounds]
  (set (mapcat (partial fabricate-compounds replacements) compounds)))

(defn fabricate-steps [{:keys [compound replacements]}]
  (some (fn [[n fabrications]] (if (get fabrications compound) n))
        (map-indexed vector (iterate (partial fabricate-all-compounds
                                              replacements)
                                     #{"e"}))))

;; So let's go backwards instead. Try applying a replacement the other
;; way around, choosing the longest, until we get back to e.

(defn invert-replacements [replacements]
  (sort #(compare (count (first %2)) (count (first %1)))
        (map reverse replacements)))

(defn defabricate [ireplacements compound]
  (some #(if-not (= compound %) %)
        (map #(apply clojure.string/replace-first compound %) ireplacements)))

(defn defabricate-steps [{:keys [compound replacements]}]
  (some (fn [[n defabrication]] (if (= defabrication "e") n))
        (map-indexed vector (iterate (partial defabricate
                                              (invert-replacements replacements))
                                     compound))))
