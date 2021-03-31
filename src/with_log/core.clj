(ns with-log.core
  (:require [clojure.tools.logging]))

(defmacro with-log [message & body]
  `(let [start#        (. System (nanoTime))
         ;; borrowed from (clojure.core/time)
         elapsed-time# #(int (/ (double (- (. System (nanoTime)) %)) 1000000.0))]
     (try
       (clojure.tools.logging/infof "BEGIN: %s" ~message)
       (let [ret# (do ~@body)]
         (clojure.tools.logging/infof
           "END (elapsed time [%d] msecs): %s"
           (elapsed-time# start#)
           ~message)

         ret#)

       (catch Throwable tw#
         (let [ex-data# (if-let [ex-data# (ex-data tw#)]
                          (str "ex-data=" ex-data#)
                          (str "no ex-data available"))]
           (clojure.tools.logging/warnf
             tw#
             "FAILED (elapsed time [%d] msecs): %s. %s"
             (elapsed-time# start#)
             ~message
             ex-data#))

         ;; Rethrow for recovery
         (throw tw#)))))

