(ns with-log.core
  (:require [clojure.tools.logging]))

(defmacro with-log [message & body]
  `(let [start# (. System (nanoTime))]
     (try
       (clojure.tools.logging/infof "BEGIN: %s" ~message)

       (let [ret# (do ~@body)]

         (clojure.tools.logging/infof
           "END (elapsed time [%d] msecs): %s"
           (int (/ (double (- (. System (nanoTime)) start#)) 1000000.0)) ;; borrowed from (clojure.core/time)
           ~message)

         ret#)
       (catch Throwable tw#
         (let [ex-data# (if-let [ex-data# (ex-data tw#)]
                          (str "ex-data=" ex-data#)
                          (str "no ex-data available"))]
           (clojure.tools.logging/warnf
             tw#
             "FAILED (elapsed time [%d] msecs): %s. %s"
             (int (/ (double (- (. System (nanoTime)) start#)) 1000000.0)) ;; borrowed from (clojure.core/time)
             ~message
             ex-data#))
         ;; Rethrow for recovery
         (throw tw#)))))

