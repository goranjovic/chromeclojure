(ns ^{:doc    "todo"
      :author "Goran Jovic"}
chromeclojure.util.chrome
  (:require [cheshire.core :as json]
            [clojure.java.io :as io])
  (:import (java.io FileOutputStream File)
           (java.util.zip ZipOutputStream ZipEntry)))

(defn manifest [options]
  {:name "Clojure Extension",
   :description "Adds an option to the context menu which allows you to eval selected Clojure code.",
   :version (:version options),
   :manifest_version 2,
   :permissions ["contextMenus"
                 "notifications"
                 (:base-url options)],
   :icons {:16 "icon-tiny.png",
           :48 "icon-small.png",
           :128 "icon-large.png"},
   :background {:scripts ["jquery-1.7.min.js" "chromeclojure.js"]},
   :content_security_policy (str "default-src 'self'; style-src 'self'; connect-src " (:base-url options) "; "),
   :options_page "options.html"})

(defn export-manifest [options]
  (spit "chrome/manifest.json"
    (-> (manifest options)
        (json/generate-string {:pretty true}))))

(defn zip
  [^String input ^String output]
  (with-open [out (-> output
                      (FileOutputStream.)
                      (ZipOutputStream.))]
    (doseq [^File f (file-seq (io/file input))]
      (when-not (.isDirectory f)
        (with-open [in (io/input-stream f)]
          (.putNextEntry out (ZipEntry. (.getPath f)))
          (io/copy in out)
          (.closeEntry out))))))


