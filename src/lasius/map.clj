(ns lasius.map)

(defn map-kv
  [f m]
  (reduce (fn [acc k]
            (assoc acc
                   k
                   (f (get m k))))
          {} (keys m)))

(defn map-v
  [f m]
  (reduce (fn [acc k]
            (assoc acc
                   k
                   (f (get m k))))
            {} (keys m)))

(defn traverse
  [f m]
  (map-v
    (fn [v]
      (if (map? v)
        (traverse f v)
        (f v))) m))

(traverse inc {:a {:b 1} :c 2})
