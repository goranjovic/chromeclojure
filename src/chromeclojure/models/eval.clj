(ns chromeclojure.models.eval
  (:use [clojail.testers :only [secure-tester-without-def]]
        [clojail.core :only [sandbox]]
        [clojure.stacktrace :only [root-cause]])
  (:use [clojure.stacktrace :only [print-stack-trace]])
  (:import (clojure.lang LazySeq)))

(defn eval-form [form sbox]
    (sbox form))

(defn stringify [result]
    (if (= LazySeq (class result))
            (str (seq result))
            (str result)))

(defn eval-string [expr sbox]
  (let [;A hack to allow reading multiple Clojure forms
        ;Well, a workaround actually, since this wraps multiple forms into one
        wrapped (str "(let [_ nil] \n" expr "\n)")
        form (binding [*read-eval* false] (read-string wrapped))]
    {:result (stringify (eval-form form sbox))
     :error false}))

(def try-clojure-tester
  (into secure-tester-without-def
        #{'chromeclojure.core}))

(defn make-sandbox []
  (sandbox try-clojure-tester
           :timeout 5000
           :init '(do (use '[clojure.repl :only [doc]])
                      (future (Thread/sleep 600000)
                              (-> *ns* .getName remove-ns)))))

(defn eval-source [s]
  (try
    (eval-string s (make-sandbox))
  (catch Exception e
    {:result (str (.getMessage e))
     :error true})))
