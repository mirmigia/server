(ns lasius.entity
  (:require
    [lasius.math :as math]))

(defn position [e]
  "gets a vector from an entity"
  (:position e))

(defn within-range? [a b distance]
  (math/within-range? (position a) (position b) distance))

