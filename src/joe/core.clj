(ns joe.core
  (:require [joe.encoding :as encoding]))

(def last-cnt (ref (long 0)))

(defn init [cnt]
  (dosync
    (ref-set last-cnt cnt)))

; {
; :url string
; :short-url string
; :n-decoded 0
; }
;

(def lock (Object.))

(defn shorten
  "Returns {:cnt new-count :url-info {...}}"
  [cnt url]
  {:id (str cnt) :url url :short-url (encoding/hex-encode cnt) :n-decoded 0})

(defn shorten! [url]
  (locking lock
    (let [s (shorten @last-cnt url)]
      (dosync
        (alter last-cnt inc))
      s)))

(defn decode-id [encoded-id]
  ""
  (-> encoded-id (Long/parseLong 16)))
