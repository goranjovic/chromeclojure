(ns chromeclojure.eval
  (:use [clojail.testers :only [secure-tester-without-def]]
        [clojail.core :only [sandbox]]
        [clojure.stacktrace :only [root-cause]])
  (:use [clojure.stacktrace :only [print-stack-trace]])
  (:import java.io.StringWriter
	   java.util.concurrent.TimeoutException))

(defn eval-form [form sbox]
  (with-open [out (StringWriter.)]
    (sbox form {#'*out* out})))

(defn eval-string [expr sbox]
  (let [form (binding [*read-eval* false] (read-string expr))]
    (eval-form form sbox)))

(def try-clojure-tester
  (into secure-tester-without-def
        #{'chromeclojure.core}))

(defn make-sandbox []
  (sandbox try-clojure-tester
           :timeout 2000
           :init '(do (use '[clojure.repl :only [doc]])
                      (future (Thread/sleep 600000)
                              (-> *ns* .getName remove-ns)))))

(defn stringify [result]
    (if (= clojure.lang.LazySeq (class result))
            (str (seq result))
            (str result)))

(defn eval-request [expr]
  (try
    (stringify (eval-string expr (make-sandbox)))
  (catch Exception e
    (with-out-str (print-stack-trace e)))))