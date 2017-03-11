(ns lasius.noise
  (:require
    [lasius.math :as m]
    [lasius.vector :as v]))

(defn- octave-ratios [octaves]
  (map #(/ % octaves) (range 1 (+ 1 octaves))))

(defn- octave-ratios->lists
  "Returns a series of lists where the lengths are based on the ratios provided by `ratios`."
  [seed width ratios]
  (map (fn [ratio]
         (take (* (m/pow width) ratio)
               (m/rand-floats (+ seed ratio))))
       ratios))

(defn noise
  "`octaves` is the amount of random lists of random floats to create (as an integer)."
  [seed width octaves]
  (->> (octave-ratios octaves)
       (octave-ratios->lists seed width)
       (v/scaled-vectors)
       (v/average-vectors)))
