(ns learnweb.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes GET]]    ; (1)
            [compojure.route :refer [not-found]])    ; (2)
  (:gen-class))

(defroutes
  app
  (GET "/" [] "<h1>Hello world!</h1>")
  (GET "/hello/:name" [name] (str "Hello " name))
  (not-found "<h1>Page not found</h1>"))    ; (3)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty app {:port 3000}))    ; (4)
