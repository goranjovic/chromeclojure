(ns chromeclojure.views.eval
  (:use [noir.core :only [defpage]]
        [chromeclojure.models.eval :only [eval-request]])
  (:require [noir.response :as resp]))

(defpage [:post "/eval"] {:keys [source jsonp]}
  (let [data (eval-request source)]
    (if jsonp
      (resp/jsonp jsonp data)
      (resp/json data))))
