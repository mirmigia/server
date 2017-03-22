(ns lasius.action)

(def mining-range 2)

(defn make-action [{:keys [scope allowed result]}]
  (fn [state action]
    (when (allowed (get-in state scope) action)
      (assoc-in state scope
                (result (get-in state scope) action)))))

(make-action
  {:name "navigate"
   :scope (fn [action]
            [:ship (id action)])

   :allowed (fn [ship action]
              (within-range?
                ship
                (:position action)
                (s/navigation-distance ship)))

   :result (fn [ship action]
             (mod-assoc ship [:move :count :steps] 15))})

(def action
  {:name :mine
   :ship 87656
   :planet {:id 5678
            :resource 5678}})

(make-action
  {:name "mine"

   ; allowed to mine when nearby planet
   :allowed (fn [game {:keys mine miner}]
              (e/within-range? mine miner mining-range))

   :result (fn [game {:keys mine miner :as action}]
             (mod-assoc
               game
               [:mine mine miner]
               {:mine mine
                :miner miner
                :modifiers
                {:countdown {:steps 30}}}))})


(defn get-ship
  [ship] #(get-ship % ship)
  [ship state]
  (get-in state
          [:ships
           (id ship)]))

(defn id [value]
  (cond
    (number? value) value
    (map? value) (:id value)
    ;consider throwing an error when id is not defined
    ))

