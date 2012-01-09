(ns joe.storage)

(def in-memory (ref []))

(defn store! [new-url]
  (dosync
    (alter in-memory conj new-url)))

(defn get-by-id [id]
  (-> (filter #(= id (:id %1)) @in-memory) first))
