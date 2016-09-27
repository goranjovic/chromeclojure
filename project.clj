(defproject chromeclojure "0.0.1-SNAPSHOT"
  :description "A Clojure Plugin for Google Chrome."
  :dependencies [[org.clojure/clojure "1.7.0"]
		             [commons-lang/commons-lang "2.5"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.4.0"]
                 [io.aviso/rook "0.1.39"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.slf4j/slf4j-log4j12 "1.7.12"]
                 [log4j/log4j "1.2.17"]
                 [clojail "1.0.6"]]
  :jvm-opts ["-Djava.security.policy=example.policy""-Xmx80M"]
  :main chromeclojure.server
  :uberjar-name "chromeclojure.jar")


