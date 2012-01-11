(ns joe.web
  (:use noir.core
        joe.core)
  (:require [noir.server :as server]
            [noir.response :as response]
            [joe.storage :as storage]))

(defn update-decodings! [info]
  (let [updated (update-in info [:decoded] inc)]
    (storage/update! updated)
    updated))

(defn respond-with-found-info [info]
  (response/json info))

(defn decode-url! [id]
  (if-let [url (storage/get-by-short-id id)]
    (respond-with-found-info url)
    (response/status 404 "Huh?")))

(defpage "/i/:id" {:keys [id]}
  (decode-url! id))

(defn save-url! [url]
  (when-let [shrt (shorten! url)]
    (storage/store! shrt)
    (response/json shrt)))

(defpage [:put "/s"] {:keys [url]}
  (save-url! url))

(defpage [:post "/s"] {:keys [url]}
  (save-url! url))

(defn redirect-to-url [id]
  (when-let [url (:url (storage/get-by-short-id id))]
    (response/redirect url)))

(defpage "/s/:id" {:keys [id]}
  (-> id storage/get-by-short-id update-decodings!)
  (redirect-to-url id))

(defn -main [& args]
  (init 0)
  (server/start 8888))
