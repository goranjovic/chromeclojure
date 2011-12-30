(ns chromeclojure.server 
  (:use [clojure.data.json :only [json-str]])
  (:use [ring.util.servlet :only [defservice]])
  (:use [compojure.core :only [defroutes GET POST ANY wrap!]])
  (:require [compojure.route :as route])
  (:use [ring.middleware params keyword-params nested-params])
  (:use [ring.adapter.jetty :only [run-jetty] :as jetty])
  (:use [ring.util.response :only [redirect]])
  (:use chromeclojure.eval))

(defroutes routes
   (ANY "/eval" [source] (json-str (eval-request source)))
   (route/resources "/")
   (route/not-found "Not Found!"))

(defn api [routes]
    (-> routes
            wrap-keyword-params
            wrap-nested-params
            wrap-params))

(defn to-port [s]
    (when-let [port s] (Long. port)))

(defn start [& [port]]
    (jetty/run-jetty (api routes)
        {:port (or (to-port port)
                   (to-port (System/getenv "PORT")) ;; For deploying to Heroku
                    8081)
         :join? false}))

(defn -main [& args] (start (first args)))
