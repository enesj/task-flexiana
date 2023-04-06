(ns task-flexiana.app
  "FIXME: my new org.corfield.new/scratch project.")

(defn exec
  "Invoke me with clojure -X task-flexiana.app/exec"
  [opts]
  (println "exec with" opts))

(defn -main
  "Invoke me with clojure -M -m task-flexiana.app"
  [& args]
  (println "-main with" args))
