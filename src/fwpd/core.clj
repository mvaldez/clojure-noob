(ns fwpd.core)

(defprotocol Foo
  (bar [x]))

(extend-type java.lang.String
  Foo
  (bar [x] (str "hello, " x)))

(bar (str "world"))

(comment (bar {}))

(def my-map {:a 1, :b 2})
(println (my-map :a))
(println (:a my-map))
(remove nil? [nil 1 2 3])
(println ((fnil int 1) nil))

;; protocol + records
(defprotocol Human
  (say-hello [x]))

(defrecord American [first-name last-name]
  Human
  (say-hello [x]
    (str "hello, my name is " first-name " " last-name)))

(defrecord Mexican [first-name last-name]
  Human
  (say-hello [x]
    (str "hola, mi nombre es " first-name " " last-name)))

(println (say-hello (map->American
                     {:first-name "Joe"
                      :last-name "Smith"
                      :city "Austin"})))

(println (say-hello (map->Mexican
                     {:first-name "Jose"
                      :last-name "Martinez"
                      :city "Cancun"})))

;; alternate ways of instantiating the record
(comment
  (println (say-hello (Mexican.  "Jose"  "Martinez" "Cancun")))) ;; fails with no matching ctor
(comment
  (println (say-hello (->Mexican  "Jose"  "Martinez" "Cancun")))) ;; fails wrong number of args

;; reify is to defrecord what fn is to defn
(def donald (reify Human (say-hello [_] (println "Quack!"))))
(say-hello donald)

;; the goal is to solve "The Expression Problem" that is to have the ability
;; to seamlessly add new types of data that work with existing functions, and new functions that
;; work seamlessly with existing data.
