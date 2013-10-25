(defproject thingsdb-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://localhost"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-json "0.1.2"]
                 [hiccup "1.0.0"]
                 [com.novemberain/monger "1.5.0"]
                 [cheshire "4.0.3"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler thingsdb-clj.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
