(defproject chromeclojure "0.0.1-SNAPSHOT"
  :description "A Clojure Plugin for Google Chrome."
  :dependencies [[org.clojure/clojure "1.3.0"]
		 [commons-lang/commons-lang "2.5"]
                 [ring/ring-jetty-adapter "0.3.7"]
                 [noir "1.2.1"]
                 [clojail "0.5.1"]]
  :jvm-opts ["-Djava.security.policy=example.policy""-Xmx80M"]
  :main chromeclojure.server)


