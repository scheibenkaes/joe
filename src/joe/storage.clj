(ns joe.storage)

(def in-memory (ref []))

(defn store! [new-url]
  (dosync
    (alter in-memory conj new-url)))

(defn get-by-id [id]
  (-> (filter #(= id (:id %1)) @in-memory) first))

(def update-lock (Object.))

(defn update! [info]
  (locking update-lock
    (let [without-this (filter #(not= (:id %1) (:id info)) @in-memory)]
      (dosync
        (ref-set in-memory (conj without-this info))))))
