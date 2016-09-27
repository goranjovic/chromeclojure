(ns chromeclojure.views.eval
  (:require [ring.util.response :as r]
            [chromeclojure.models.eval :as service]))

(defn eval-source
  {:route        [:post []]
   :description  "Eval"}
  [params]
  (let [data (service/eval-source (:source params))]
    (r/response data)))
