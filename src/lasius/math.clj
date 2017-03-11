(ns lasius.math)

(defn abs [v]
  (Math/abs v))

(defn delta [x y]
  (abs (- x y)))

(defn pow
  ([v] (pow v 2))
  ([v exp] (Math/pow v exp)))

(defn floor
  "returns a number rounded down to an integer"
  [x]
  (int (Math/floor x)))

(defn squared-distance [a b]
  (reduce + (map (comp pow delta) a b)))

(defn distance
  [a b]
  (Math/sqrt (squared-distance a b)))

(defn within-range? [a b distance]
  (< (squared-distance a b) (pow distance)))

(defn within? [a b x]
  (< a x b))

(defn average [nums]
  (/ (apply + nums) (count nums)))

(defn ceil [n]
  (Math/ceil n))

(defn rand-floats [seed]
  (repeatedly
    (let [gen (java.util.Random. seed)]
      #(.nextFloat gen))))
