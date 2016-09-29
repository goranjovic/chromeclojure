(ns chromeclojure.server 
  (:use [ring.middleware.file :only [wrap-file]]
        [compojure.core])
  (:require [ring.adapter.jetty :as jetty]
            [io.aviso.rook :as rook]
            [compojure.route :as route]
            [clojure.tools.logging :as log]
            [ring.util.response :as r]
            [ring.middleware.content-type :as content-type]
            [chromeclojure.views.eval])
  (:gen-class))

(defn wrap-with-rest-middleware [handler]
  (-> handler
      (ring.middleware.format/wrap-restful-format :formats [:json-kw])
      ring.middleware.keyword-params/wrap-keyword-params
      ring.middleware.params/wrap-params))

(defn static-file-handler
  [request]
  (let [path (-> request :params :filename)
        index? (#{nil "" "/"} path)
        filename (if index? "index.html" path)]
    (if-let [rsp (r/resource-response filename {:root "public"})]
      (if index?
        (r/content-type rsp "text/html")
        (content-type/content-type-response rsp request)))))

(defn rest-handler []
  (wrap-with-rest-middleware
    (rook/namespace-handler
        {:context ["api" "v1"]}
        ["eval"  'chromeclojure.views.eval])))

(defn create-handler []
  (let [rest (rest-handler)]
    (routes
      (ANY "/api/*" [] rest)
      (GET ["/:filename"  :filename #".*"] []
        #'static-file-handler)
      (route/not-found "Not found"))))

(defn -main [& args]
  (let [port (or (System/getenv "PORT") "8081")]
    (log/info "Starting chromeclojure server")
    (jetty/run-jetty (create-handler) {:port (new Long port)
                                       :join? false})))
