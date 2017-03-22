(ns lasius.ship)

(defn fits-warehouse?
  [ship resource]
  "todo"
  [ship resource & resources]
  "todo"
  )

(defn expand-warehouse
  [ship resources]
  (assoc ship :warehouse
         (conj (:warehouse ship) resources)))

(defn make-ship [defs]
  "create a ship"
  (merge {:type ship} defs))
