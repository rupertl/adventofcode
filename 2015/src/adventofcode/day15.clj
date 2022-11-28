(ns adventofcode.day15
  (:gen-class))

;; Day 15: Science for Hungry People

;; Today, you set out on the task of perfecting your milk-dunking
;; cookie recipe. All you have to do is find the right balance of
;; ingredients.

;; Your recipe leaves room for exactly 100 teaspoons of ingredients.
;; You make a list of the remaining ingredients you could use to
;; finish the recipe (your puzzle input) and their properties per
;; teaspoon:

;;     capacity (how well it helps the cookie absorb milk)
;;     durability (how well it keeps the cookie intact when full of milk)
;;     flavor (how tasty it makes the cookie)
;;     texture (how it improves the feel of the cookie)
;;     calories (how many calories it adds to the cookie)

;; You can only measure ingredients in whole-teaspoon amounts
;; accurately, and you have to be accurate so you can reproduce your
;; results in the future. The total score of a cookie can be found by
;; adding up each of the properties (negative totals become 0) and
;; then multiplying together everything except calories.

;; Given the ingredients in your kitchen and their properties, what is
;; the total score of the highest-scoring cookie you can make?

(defn ingredient-score [ingredients item property]
  (property (item ingredients)))

(defn property-score [ingredients weights property]
  (max 0 (apply + (map #(* (% weights)
                           (ingredient-score ingredients % property))
                       (keys ingredients)))))

(defn cookie-score [ingredients weights]
  (apply * (map (partial property-score ingredients weights)
                [:capacity :durability :flavor :texture])))

;; Can't see an easy way to do this easily for n ingredients

(defn possible-weights [ingredients]
  (if (= (count ingredients) 2)
    (for [a (range 101)
          :let [b (- 100 a)
                weights (zipmap (keys ingredients) (list a b))]
          :when (>= 100 (+ a b))]
      weights)
    (for [a (range 101) b (range 101) c (range 101)
          :let [d (- 100 a b c)
                weights (zipmap (keys ingredients) (list a b c d))]
          :when (>= 100 (+ a b c d))]
      weights)))

(defn max-cookie-score [ingredients]
  (apply max (map (partial cookie-score ingredients)
                  (possible-weights ingredients))))


;; Part Two

;; Your cookie recipe becomes wildly popular! Someone asks if you can
;; make another recipe that has exactly 500 calories per cookie (so
;; they can use it as a meal replacement). Keep the rest of your
;; award-winning process the same (100 teaspoons, same ingredients,
;; same scoring system).

;; For example, given the ingredients above, if you had instead
;; selected 40 teaspoons of butterscotch and 60 teaspoons of cinnamon
;; (which still adds to 100), the total calorie count would be 40*8 +
;; 60*3 = 500. The total score would go down, though: only 57600000,
;; the best you can do in such trying circumstances.

;; Given the ingredients in your kitchen and their properties, what is
;; the total score of the highest-scoring cookie you can make with a
;; calorie total of 500?

(defn has-500-cals [ingredients weights]
  (= (property-score ingredients weights :calories) 500))

(defn max-cookie-score-500-cals [ingredients]
  (apply max (map (partial cookie-score ingredients)
                  (filter (partial has-500-cals ingredients)
                          (possible-weights ingredients)))))
