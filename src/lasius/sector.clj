(ns lasius.sector
  (:require
    [lasius.math :as m]
    [lasius.noise :as n]))

(def sector-width 32)

(def sector-square (m/pow sector-width))

(defn sector-seed [x y seed]
  "returns a seed based on the sector coordinates and the global seed"
  (* x y seed))

(defn sector-position
  "returns the absolute position of a sectors top left corner"
  [x y]
  ([(* sector-width x)
    (* sector-width y)]))

(defn index->position [index]
  "returns a position relative to the sector's top left corner based on the index of the sector pixel"
  [(mod index sector-width)
   (m/floor (/ index sector-width))])

(defn ratio->index [ratio]
  "return an integer based on a ratio. This is used to determine what index to use for getting the ratio and position"
  (m/floor (* sector-square ratio)))

(defn noise->positions
  "returns a list of positions based on noise and a place? predicate."
  ([noise place?] (noise->positions noise place? []))
  ([noise place? celestials]
   (:positions
     (reduce
       (fn [{:keys [index positions]} ratio]
         (let [i (ratio->index ratio)
               r (nth noise i)
               p (index->position i)]
           {:positions (if (place? r p positions)
                         (concat [p] positions)
                         positions)
            :index (+ 1 index)}))
       {:index 0
        :positions []}
       noise))))
