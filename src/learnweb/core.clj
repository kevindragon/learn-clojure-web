(ns learnweb.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5]])
  (:gen-class))

(def form-html
  (html5
    (html
      [:body
       [:h1 "Hello form"]
       [:form {:action "/message" :method "post"}
        [:div
         [:label {:for "name"} "名字:"]
         [:input {:type "text"
                  :name "name"}]]
        [:div
         [:label {:for "message"} "消息:"]
         [:textarea {:cols 80 :rows 5 :name "message"}]]
        [:div
         [:input {:type "submit" :value "提交"}]]]])))

(defn show-message [params]
  (let [{name "name" message "message"} params]
    (html5
      (html
        [:body
         [:h2 name]
         [:p message]]))))

(defroutes
  app
  (GET "/" [] form-html)
  (POST "/message" {params :params} (show-message params))
  (GET "/hello/:name" [name] (str "Hello " name))
  (not-found "<h1>Page not found</h1>"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty (wrap-reload (wrap-params #'app)) {:port 3000}))
