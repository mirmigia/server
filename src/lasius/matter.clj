(ns lasius.matter
  (:require
    [lasius.math :as math]))

(def ^:private properties
  (atom []))

;; FIXME: returns the value of @properties
(defn defproperty [id definition]
  (let [prop (merge definition {:id id})]
    (swap! properties conj prop)
    prop))

(def particle-profile-size 12)

(def particle-keys [:e :p :n])

(defn particles-profile-particle-ratios [particles profile]
  (map-indexed
    (fn [i v] (nth (nth profile i) v))
    particles))

(defn particle-profile-ratio [particles profile]
  "returns a ratio based on the overlap between the particles and the property
  profile. If one of the particles is 0 it returns 0 as ratio"
  (let [ratios (particles-profile-particle-ratios particles profile)]
    (if (some #{0} ratios)
      0
      (/ (reduce + ratios) (count particle-keys)))))

(defn particle-profile-between [a b]
  "Cretes an array which represents a particle profile"
  (map-indexed
    (fn [i v] (if (math/between? a b i) 1 0))
    (repeat particle-profile-size 0)))

(defn make-particles
  ([n]
  "returns particles based on random integer"
   (repeatedly n #(rand-int 12)))
  #_([psuedo]
   "returns particles based on psuedo random integers"
   (repeatedly 3 #(rand-int 12))))

(defn make-matter []
  "returns a list of maps that contain the property identiefier and the ratio
  in which the matter possess it"
  (let [particles (make-particles)]
    {:particles particles
     :properties (map (fn [property]
                        {:property (:id property)
                         :ratio (particle-profile-ratio particles (:profile property))})
                      @properties)}))

;;
;; Property definitions
;;
(m/defproperty :elastic
  {:name    "Elasticity"
   :profile [(m/particle-profile-between 3 8)
             (m/particle-profile-between 3 8)
             (m/particle-profile-between 3 8)]})

(m/defproperty :incendiary
  {:name    "Incendary"
   :profile [(m/particle-profile-between 1 5)
             (m/particle-profile-between 1 5)
             (m/particle-profile-between 1 5)]})

(m/defproperty :hard
  {:name     "Hardness"
   :profile [(m/particle-profile-between 7 9)
             (m/particle-profile-between 6 8)
             (m/particle-profile-between 5 7)]})

(m/defproperty :reflective
  {:name     "Reflectiveness"
   :profile [(m/particle-profile-between 7 9)
             (m/particle-profile-between 6 8)
             (m/particle-profile-between 5 7)]})
