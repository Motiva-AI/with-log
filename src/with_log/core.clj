(ns with-log.core
  (:require [clojure.tools.logging]))

(defmacro with-log [message & body]
  `(try
     (clojure.tools.logging/infof "BEGIN: %s" ~message)
     (let [ret# (do ~@body)]
       (clojure.tools.logging/infof "END: %s" ~message)
       ret#)
     (catch Throwable tw#
       (let [ex-data# (if-let [ex-data# (ex-data tw#)]
                        (str "ex-data=" ex-data#)
                        (str "no ex-data available"))]
         (clojure.tools.logging/warnf tw# "FAILED: %s. %s" ~message ex-data#))
       ;; Rethrow for recovery
       (throw tw#))))
