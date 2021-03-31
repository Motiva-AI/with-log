(defproject motiva/with-log "0.3.0-SNAPSHOT"
  :description "Log macro for capturing errors and timings"
  :url "https://github.com/Motiva-AI/with-log"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.3"]]

                   :plugins []}

             :provided {:dependencies [[org.clojure/tools.logging "1.1.0"]]}}

  :repositories {"releases" {:url           "https://clojars.org/repo"
                             :username      "motiva-ai"
                             :password      :env
                             :sign-releases false}})

