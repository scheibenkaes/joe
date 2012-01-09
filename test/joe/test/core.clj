(ns joe.test.core
  (:use [joe.core])
  (:use [clojure.test]))

(deftest shorten-returns-a-map 
  (is (map? (shorten 1 "asdf"))))

(deftest shorten!-returns-a-map
  (is (map? (shorten! "some"))))

(deftest shorten!-increments-last-cnt
  (let [before @last-cnt
        after (shorten! "http://foo.bar")]
    (is (> @last-cnt before))))

(deftest test-decode
  (is (= 10 (decode-id "a"))))
