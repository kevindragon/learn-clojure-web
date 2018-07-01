(ns learnweb.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5]]
            [selmer.parser :refer [render-file]])
  (:gen-class))

(defn show-message [params]
  (let [{name "name" message "message"} params]
    (render-file "message.html" {:name name :message message})))

(defroutes
  app
  (GET "/" [] (render-file "index.html" {}))
  (POST "/message" {params :params} (show-message params))
  (GET "/hello/:name" [name] (str "Hello " name))
  (GET "/home" [] (render-file "home.html" {}))
  (not-found "<h1>Page not found</h1>"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty (wrap-reload (wrap-params #'app)) {:port 3000}))
