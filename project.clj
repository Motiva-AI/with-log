(defproject motiva/with-log "0.1.2"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [com.unbounce/clojure-dogstatsd-client "0.5.0"]]

  :profiles {:dev {:plugins [[s3-wagon-private "1.3.1"]]}}

  ;; Use the chained credential provider - env credentials or a profile (set
  ;; AWS_PROFILE)
  ;; https://github.com/s3-wagon-private/s3-wagon-private#aws-credential-providers
  :repositories {"private" {:url "s3p://maven-private-repo/releases/"
                            :no-auth true
                            :sign-releases false}})
