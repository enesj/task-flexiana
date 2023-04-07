(ns task-flexiana.app-test
  (:require [clojure.test :as t :refer [deftest is testing use-fixtures]]
            [task-flexiana.fixtures :refer [start-jetty]]
            [task-flexiana.handlers :as handlers]
            [clj-http.client :as client]))

(use-fixtures :once start-jetty)

(deftest scramble?-test
  (testing "scramble?"
    (is (handlers/scramble? ["rekqodlw" "world"]))
    (is (not (handlers/scramble? ["rekqodl" "world"])))
    (is (handlers/scramble? ["cedewaraaossoqqyt" "codewars"]))
    (is (not (handlers/scramble? ["katser" "katas"])))))

(deftest scramble-test
  (testing "scramble?"
    (is (= "true"
           (:body (handlers/scramble {:params {:str1 "rekqodlw" :str2 "world"}}))))
    (is (= "false"
           (:body (handlers/scramble {:params {:str1 "rekqodl" :str2 "world"}}))))
    (is (= "true"
           (:body (handlers/scramble {:params {:str1 "cedewaraaossoqqyt" :str2 "codewars"}}))))
    (is (= "false"
           (:body (handlers/scramble {:params {:str1 "katser" :str2 "katas"}}))))
    (is (= "Please provide both str1 and str2 parameters"
           (:body (handlers/scramble {:params {:str1 "katser"}}))))))

(deftest scramble-http-test
  (testing "scramble?"
    (is (= "true" (:body (client/get "http://localhost:3000/scramble?str1=rekqodlw&str2=world"))))
    (is (= "false" (:body (client/get "http://localhost:3000/scramble?str1=rekqodl&str2=world"))))
    (is (= "true" (:body (client/get "http://localhost:3000/scramble?str1=cedewaraaossoqqyt&str2=codewars"))))
    (is (= "false" (:body (client/get "http://localhost:3000/scramble?str1=katser&str2=katas"))))
    (is (thrown? Exception (client/get "http://localhost:3000/scramble?str1=rekqodlw")))))




