(ns joe.web
  (:use noir.core
        joe.core)
  (:require [noir.server :as server]
            [noir.response :as response]
            [joe.storage :as storage]))

(defpage "/d/:id" {:keys [id]}
  (if-let [url (storage/get-by-id id)]
    (response/json url)
    (response/status 404 "Huh?")))

(defpage [:post "/s"] {:keys [url]}
  (when-let [shrt (shorten! url)]
    (storage/store! shrt)
    (response/json shrt)))

(defn -main [& args]
  (server/start 8888))
