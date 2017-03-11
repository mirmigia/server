(ns lasius.univers
  (:require
    [lasius.sector :as s]))

(def celestials (atom []))

(def seed 9872)

(def planet-min-distance 8)

(defn place-planet? [ratio position positions]
  "returns true when the planet is allowed to be placed. A planet is not
  allowed to be placed within a minimal range of other planets"
  (and
    (> ratio 0.8)
    (not (some #(m/within-range? position % planet-min-distance) positions))))


(defn planet-noise [x y seed]
  (n/make-noise seed sector-width 12))

(defn make-planet [defs]
  (merge {:type :planet} defs))

(defn make-planets [noise]
  (reduce (fn [v]
            (when (> v 0.8) make-planet))))
