(ns with-log.core-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging.test :as log.test]
            [with-log.core :refer [with-log]]))

(deftest with-log-test
  (let [msg "test message 123"]
    (testing "happy path"
      (log.test/with-log
        (is (= :foo (with-log msg :foo)))

        (is (log.test/logged? 'with-log.core-test :info (str "BEGIN: " msg)))
        (is (log.test/logged? 'with-log.core-test :info (re-pattern (str "END \\(elapsed time \\[\\d+\\] msecs\\): " msg))))))

    (testing "failed path"
      (let [fail-message "testing fail case"
            e            (Exception. fail-message)]
        (log.test/with-log
          (is (thrown-with-msg?
                Exception
                (re-pattern fail-message)
                (with-log msg ((fn [] (throw e))))))

          (is (log.test/logged? 'with-log.core-test :info (str "BEGIN: " msg)))
          (is (log.test/logged? 'with-log.core-test :warn e (re-pattern (str "FAILED \\(elapsed time \\[\\d+\\] msecs\\): " msg)))))))))

