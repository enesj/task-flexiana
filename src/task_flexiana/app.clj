(ns task-flexiana.app
  (:require
    [ring.adapter.jetty :as jetty]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.keyword-params :refer [wrap-keyword-params]]
    [task-flexiana.handlers :refer [home not-found scramble]]))

(defn router
  [request]
  (case (:uri request)
    "/" (home request)
    "/scramble" (scramble request)
    (not-found request)))

(def app
  (-> #'router
      wrap-keyword-params
      wrap-params))

(defonce server (atom nil))

(def jetty-opts {:port 3000 :join? false})

(defn start! [opts]
  (reset! server
   (jetty/run-jetty (fn [r] (app r)) opts)))


(defn stop! []
  (when-some [s @server]
    (.stop s)
    (reset! server nil)))

(defn reset-server! []
  (stop!)
  (start! jetty-opts))

(defn -main
  [& args]
  (start! jetty-opts))


(comment

 (start! jetty-opts)

 (stop!)

 (reset-server!)

 #_())


