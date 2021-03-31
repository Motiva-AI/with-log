(ns with-log.core
  (:require [clojure.tools.logging]))

(defmacro with-log [message-or-config & body]
  (if (map? message-or-config)
    ;; Allow passing of some metric info
    (let [{:keys [message]}
          message-or-config]
      `(with-log ~message
         ~@body))

    `(try
       (clojure.tools.logging/infof "BEGIN: %s" ~message-or-config)
       (let [ret# (do ~@body)]
         (clojure.tools.logging/infof "END: %s" ~message-or-config)
         ret#)
       (catch Throwable tw#
         (let [ex-data# (if-let [ex-data# (ex-data tw#)]
                          (str "ex-data=" ex-data#)
                          (str "no ex-data available"))]
           (clojure.tools.logging/warnf tw# "FAILED: %s. %s" ~message-or-config ex-data#))
         ;; Rethrow for recovery
         (throw tw#)))))

