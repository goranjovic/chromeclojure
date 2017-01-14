(ns chromeclojure.views.eval
  (:require [ring.util.response :as r]
            [chromeclojure.service.eval :as service]))

(defn eval-source
  {:route        [:post []]
   :description  "Eval"}
  [request params]
  (let [token (get-in request [:headers "token"])
        data (service/eval-source token (:source params))]
    (r/response data)))
