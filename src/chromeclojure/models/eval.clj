(ns chromeclojure.models.eval
  (:use [clojail.testers :only [secure-tester-without-def]]
        [clojail.core :only [sandbox]]
        [clojure.stacktrace :only [root-cause]])
  (:use [clojure.stacktrace :only [print-stack-trace]])
  (:require [noir.session :as session])
  (:import java.io.StringWriter
	   java.util.concurrent.TimeoutException))

(defn eval-form [form sbox]
    (sbox form))

(defn stringify [result]
    (if (= clojure.lang.LazySeq (class result))
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

(defn find-sb [old]
  (if-let [sb (get old "sb")]
    old
  (assoc old "sb" (make-sandbox))))

(defn eval-request [expr]
  (try
    (eval-string expr (get (session/swap! find-sb) "sb"))
  (catch Exception e
    {:result (str (root-cause e))
     :error true})))
