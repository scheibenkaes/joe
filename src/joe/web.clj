(ns joe.web
  (:use noir.core
        joe.core)
  (:require [noir.server :as server]
            [noir.response :as response]
            [joe.storage :as storage]))

(defn respond-with-found-info [info]
  (do 
    (let [updated (update-in info [:n-decoded] inc)]
      (storage/update! updated)
      (response/json updated))))

(defn decode-url! [id]
  (if-let [url (storage/get-by-id id)]
    (respond-with-found-info url)
    (response/status 404 "Huh?")))

(defpage "/d/:id" {:keys [id]}
  (decode-url! id))

(defn save-url! [url]
  (when-let [shrt (shorten! url)]
    (storage/store! shrt)
    (response/json shrt)))

(defpage [:put "/s"] {:keys [url]}
  (save-url! url))

(defpage [:post "/s"] {:keys [url]}
  (save-url! url))

(defn -main [& args]
  (init 0)
  (server/start 8888))
