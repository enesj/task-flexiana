(ns task-flexiana.main
  (:require
    [ajax.core :refer [GET POST]]
    [reagent.core :as r]
    [reagent.dom :as rdom]))

(def state (r/atom {:str1 nil :str2 nil :result nil}))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "Error" status " " status-text)))

(defn scramble [[str1 str2]]
  (GET "http://localhost:3000/scramble" {:params {:str1 str1 :str2 str2}
                                         :handler #(swap! state assoc :result %)
                                         :error-handler error-handler}))

(def title "Scrambles challenge")

(defn scramble-form
  "Form to enter two strings and scramble them."
  []
  [:div
   [:input {:type "text"
            :placeholder "Enter string 1"
            :value (:str1 @state)
            :on-change #(swap! state assoc :str1 (.-value (.-target %)))}]
   [:input {:type "text"
            :placeholder "Enter string 2"
            :value (:str2 @state)
            :on-change #(swap! state assoc :str2 (.-value (.-target %)))}]
   [:button {:on-click #(scramble (map val @state))} "Scramble!"]])

(defn home []
  [:div
   [:h1 title]
   [scramble-form]
   [:h2 (str "Result: "(:result @state))]])

(defn start
  []
  (rdom/render
    [home]
    (.getElementById js/document "app")))

(start)

