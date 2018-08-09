(ns with-log.core
  (:require [clojure.tools.logging]))

(defmacro with-log [message & body]
  `(try
     (clojure.tools.logging/infof "BEGIN: %s" ~message)
     (let [ret# (do ~@body)]
       (clojure.tools.logging/infof "END: %s" ~message)
       ret#)
     (catch Throwable tw#
       (clojure.tools.logging/warnf tw# "FAILED: %s" ~message)
       ;; Rethrow for recovery
       (throw tw#))))
