(ns lasius.vector
  (:require
    [lasius.math :as m]))

(defn scaled-vector [v scale]
  "returns a vector where the vector is stretched to a width"
  (mapcat #(repeat (m/ceil scale) %) v))

(defn vector-scale [a b]
  "returns the sacle(count ratio of two vectors"
  (/ (count a) (count b)))

; todo: find out to pass n args
(defn scaled-vectors [scale colls]
  "returns a vector where all vectors are scaled to the largest vector, summed
  and divided by the amount of vectors passed"
  (let [m (apply max (map count colls))]
    (->> colls
         (map #(/ m (count %)))
         (map scaled-vector))))

(defn average-vectors [colls]
  (map m/average colls))
