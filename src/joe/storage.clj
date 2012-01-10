(ns joe.storage)

(def in-memory (ref []))

(defn store! [new-url]
  (dosync
    (alter in-memory conj new-url)))

(defn get-by-short-id [short-id]
  (-> (filter #(= short-id (:short-url %1)) @in-memory) first))

(defn get-by-id [id]
  (-> (filter #(= id (:id %1)) @in-memory) first))

(defn update! [info]
  (let [without-this (filter #(not= (:id %1) (:id info)) @in-memory)]
    (dosync
      (ref-set in-memory (conj without-this info)))))
