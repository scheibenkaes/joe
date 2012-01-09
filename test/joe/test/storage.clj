(ns joe.test.storage
  (:use [clojure.test]
        [joe.storage]))

(deftest test-store!-and-get
  (let [data {:id 123 :test-value "asdf"}]
    (do
      (store! data)
      (is (nil? (get-by-id 1)))
      (is (= "asdf" (-> (get-by-id 123) :test-value))))))

(deftest update-updates-the-given-values
  (do
    (dosync
      (ref-set in-memory []))
    (store! {:id "1" :url "none"})
    (store! {:id "2" :url "google.de"})
    (update! {:id "1" :url "google.de"})
    (is (= 2 (count @in-memory)))
    (is (= "google.de" (-> (get-by-id "1") :url)))))
