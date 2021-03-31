(defproject motiva/with-log "0.3.0-SNAPSHOT"
  :description "Log macro for capturing errors and timings"
  :url "https://github.com/Motiva-AI/with-log"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.0"]]

                   :plugins [[s3-wagon-private "1.3.1"]]}

             :provided {:dependencies [[org.clojure/tools.logging "0.4.1"]]}}

  ;; Use the chained credential provider - env credentials or a profile (set
  ;; AWS_PROFILE)
  ;; https://github.com/s3-wagon-private/s3-wagon-private#aws-credential-providers
  :repositories {"private" {:url "s3p://maven-private-repo/releases/"
                            :no-auth true
                            :sign-releases false}})
