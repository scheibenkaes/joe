(ns joe.test.encoding
  (:use [clojure.test]
        [joe.encoding]))

(deftest test-hex-encode
  (is (= "a" (hex-encode 10)))
  (is (= "1" (hex-encode 1))))

(deftest test-hex-encode-throws-on-invalid-input
  (is (thrown? Exception (hex-encode "asf"))))
