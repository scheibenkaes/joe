(ns joe.test.storage
  (:use [clojure.test]
        [joe.storage]))

(deftest test-store!-and-get
  (let [data {:id 123 :test-value "asdf"}]
    (do
      (store! data)
      (is (nil? (get-by-id 1)))
      (is (= "asdf" (-> (get-by-id 123) :test-value))))))
