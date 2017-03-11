(ns lasius.entity
  (:require
    [lasius.math :as math]))

(defn position [e]
  "gets a vector from an entity"
  [(:x e) (:y e)])

(defn within-range? [a b distance]
  (math/within-range? (position a) (position b) distance))

(defn make-ship [defs]
  "create a ship"
  (merge {:type ship} defs))
