(ns lasius.modifier
  (:require
    [lasius.ship :as s]
    [lasius.map :as m]))
; helpers

(def state
  {:a 20 :modifiers {:b 29}})

(defn mod-path
  ([ks]
   (concat [:modifiers] (interpose :modifiers ks)))
  ([ks nks]
   (concat (mod-path ks) nks)))

(mod-path [:a]); => (:modifiers :a)
(mod-path [:a :b]); => (:modifiers :a :modifiers :b)
(mod-path [:a] [:b :c]); => (:modifiers :a :b :c)

(defn mod-fn
  [state path f]
  (assoc-in state path (f (get-in state path))))

(mod-fn state (mod-path[:b]) inc); => {:a 20 :modifiers {:b 30}}

(defn mod-get
  "inspired by get-in. Returns the value of the modifiers"
  [state path]
   (get-in state path))

(mod-get state (mod-path [:b])); => 29

(defn mod-dissoc
  [state path]
  (mod-assoc state (butlast path)
             (dissoc (mod-get state (butlast path))
                     (last path))))

(mod-dissoc state (mod-path [:b])); => {:a 20 :modifiers {}}

(defn mod-assoc [state path v]
  (assoc-in state path v))

(mod-assoc {:a {:b 2}} [:a] 4)

(defn mod-fn [state ks f]
  "Returns the value if the function(f) which is called with the value at the mod path"
  (mod-assoc state ks (f (mod-get state ks))))

(defn mod-exists? [state path]
  (not (nil? (mod-get state path))))

(mod-exists? state (mod-path [:a])); =? false
(mod-exists? state (mod-path [:b])); =? true

(def modifiers
  {
   ; :move
   ;  (fn [state]
   ;    (let [steps (mod-get state [:move :countdown] :steps)  ]
   ;      (case
   ;        nil (mod-assoc state path 15)
   ;        0 (-> state
   ;              (mod-assoc :position (mod-get state [:move :position]))
   ;              (mod-dissoc :move)))))

   ;  :mine
   ;  (fn [state]
   ;    (let [steps (mod-get state [:mine :countdown :steps])
   ;          points (get-in state [:level :mining])
   ;          resource (mod-get state [:mine :matter])]
   ;      (when (= steps 0)
   ;        (s/expand-warehouse state ))
   ;      ))
= (mod-get state [:countdown])
         ()

   ; transaction is when resources are shared between resource holding things Like mining
   :transact
   (fn [world]
     (let [{:keys [from toward]}  state]
       (if (mod-exists? [:countdown])
         state
         ()
         )
     ))

   :mine
   (fn [world]
     (let [mine-path (mod-path [] [(get-in miner [:miner :mine])])
           resource-path (mod-path [] (concat mine-path (get-in miner [:miner :resource])
                                              )
                                   [(get-in  )] )
           (mod-path [(:resource miner)])
           ]
       (

        )))

   :test
   (fn [state]
     (let [test-path (mod-path [:test])
           step-path (mod-path [:test] [:step])
           step (mod-get state step-path)]
           (cond
             (nil? step) (mod-assoc state step-path 0)
             (> step 10) (mod-dissoc state test-path)
             true (assoc-fn state step-path inc))))

   :countdown
   (fn [state]
     (let [countdown-path (mod-path [:countdown])
           step-path (mod-path [:countdown] [:step])
           step (mod-get state step-path)]
       (cond
         (<= step 0) (mod-dissoc state countdown-path)
         true (mod-fn state step-path dec))))})

(modified {:modifiers {:countdown {:step 0} }})

(defn state->state
  "takes state and returns the new state by applying modifiers recursively"
  [state]
  (traverse modified state))

(defn id->modifier [id] (get modifiers id))

(defn state->modifiers
  "Returns a list of modifier functions based on the state modifiers map"
  [state]
  (map id->modifier
       (keys (or (:modifiers state) []))))

; todo write mod registration
(defn modified
  "Returns the state after the modifiers have been applied to it. One level deep"
  ([state]
   (modified state (state->modifiers state)))
  ([state modifiers]
   ((apply comp modifiers) state)))

(modified {:modifiers {:test {}}})

