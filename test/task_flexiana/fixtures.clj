(ns task-flexiana.fixtures
  (:require [clojure.test :refer :all]
            [task-flexiana.app :refer [start! stop!]]))


(defn start-jetty
  "Fixture for starting the app with webserver and truncating PG database between tests"
  [test]
  (try
    (start! {:port 3000 :join? false})
    (test)
    (finally
      (stop!))))
