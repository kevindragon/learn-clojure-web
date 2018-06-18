(ns learnweb.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [clojure.string :refer [split]])
  (:gen-class))

(defn uri-split [str]
  (filter #(not-empty %) (split str #"/")))    ; (1)

(defn route [request]
  (let [uri (:uri request)
        seg (uri-split uri)]
    (cond
      (= uri "/") "Index page"
      (= (first seg) "hello") (str "你好" (nth seg 1 "Oops"))
      :else "Page not found")))    ; (2)

(defn handler [request]
  (let [uri (:uri request)
        seg (uri-split uri)]
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (route request)}))    ; (3)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty handler {:port 3000}))
