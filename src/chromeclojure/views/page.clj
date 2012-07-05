(ns chromeclojure.views.page
  (:use [noir.core :only [defpage]]
        [chromeclojure.models.eval :only [eval-request]])
  (:use hiccup.core)
  (:use hiccup.page-helpers)
  (:require [noir.response :as resp]))

(defpage "/" {}
 (html5 
  [:title "Clojure Extension for Chrome"]
  (include-css "/css/chromeclojure.css")
  (include-js "/js/ga.js")
  (include-js "/js/jquery.js")
  (include-js "/js/sticky.js")
  [:body
    (slurp "resources/public/js/ads/leaderboard.js")
    [:div#github-banner [:a {:href "https://github.com/goranjovic/chromeclojure" :alt "Fork chromeclojure on Github!"}]]
    [:h1 "Clojure Extension for Chrome"]
    [:h2 "Adds an option to the context menu which allows you to eval selected Clojure code."]
    [:p "Like Clojure and often read blogs about it? Often have to start up a REPL and copy & paste a snippet just to see how will it evaluate?"]
    [:p "Worry no more! Install this Chrome extension and you will have another option in your browser's context menu - Eval as Clojure. Select a piece of Clojure code, right click it and choose this option and you will instantly get a notification with evaluation results."]
    [:p (link-to "https://chrome.google.com/webstore/detail/dkhaobmljgohccicjemmbacpooaacgeo" "Install") " this extension and try it out yourself!"]
    [:div#footer (link-to "privacy.txt" "Privacy Policy") " | Copyright © 2012 All Right Reserved. Goran Jovic."]]
   ))
