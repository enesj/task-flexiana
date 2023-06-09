(ns task-flexiana.handlers)

(def cors-headers
  "Generic CORS headers"
  {"Access-Control-Allow-Origin"  "*"
   "Access-Control-Allow-Headers" "*"
   "Access-Control-Allow-Methods" "GET POST OPTIONS DELETE PUT"})

(defn not-found
  [request]
  {:status 404
   :headers {"Content-Type" "text/plain"}
   :body (str "Not Found: " (:uri request) "\n")})

(defn home
  [_request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Scrambles challenge home page\n"})

(defn scramble? [s1-2]
  (let [[s1 s2] s1-2]
    (if (empty? s2)
      true
      (let [search (str (first s2))]
        (if (.contains s1 search)
          (scramble? (mapv #(clojure.string/replace-first % (re-pattern search) "") s1-2))
          false)))))

(defn scramble
  ""
  [request]
  (let [params (select-keys (:params request) [:str1 :str2])]
    (if (= 2 (count params))
      (let [result (->> params
                        (map val)
                        scramble?
                        str)]
        {:status 200
         :headers cors-headers
         :body result})
      {:status 400
       :headers cors-headers
       :body "Please provide both str1 and str2 parameters"})))
