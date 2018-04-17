(ns needlecast.core-test
  (:require [clojure.test :refer :all]
            [jsonista.core :as json]
            [clojure.data.csv :as csv]
            [needlecast.core :refer :all]
            [needlecast.producer :as ncp]
            [needlecast.consumer :as ncc]
            [needlecast.io :as ncio]))

(def test-file-location "./data/test_input.csv")

(def test-consumer-config
  {"group.id" "avg-rate-consumer"
   "auto.offset.reset" "earliest"
   "enable.auto.commit" "true"})

(def fixture-header-row ["first_name" "last_name" "balance" "email"])

(defn post-test-msgs-from-csv-file []
  (let [kafka-producer (-> (merge (get-producer-config-from-env)
                                  ncp/string-properties-config)
                           ncp/producer-from)
        xfrm-fn (comp (partial ncp/send! kafka-producer "test")
                      json/write-value-as-string
                      (partial zipmap fixture-header-row))]
    (do
      (csv-file->xfrm test-file-location xfrm-fn)
      (.close kafka-producer))))

(defn post-test-msgs []
  (let [kafka-producer (-> (merge (get-producer-config-from-env)
                                  ncp/string-properties-config)
                           ncp/producer-from)
        msgs [{:first-name "Dave"
               :last-name "Lister"
               :balance 0.0
               :email "davel@example.com"}
              {:first-name "Arnold"
               :last-name "Rimmer"
               :balance 2.50
               :email "acerimmer@example.com"}]]
    (do
      (map #(->> (json/write-value-as-string %)
                 (ncp/send! kafka-producer "test"))
           msgs)
      (.close kafka-producer))))

(def read-buffer (atom []))

(defn reset-read-buffer [f]
  (f)
  (reset! read-buffer []))

(defn poll-for-test-msgs []
  (let [kafka-consumer (-> (merge (get-consumer-config-from-env)
                                  ncc/string-properties-config
                                  test-consumer-config)
                           (ncc/consumer-from "test"))
        min-batch-size 3]
    (when (< (count read-buffer)
             min-batch-size)
      (let [records (.poll consumer 100)]
        (swap! read-buffer into records)
        (if-not (>= (count read-buffer)
                min-batch-size)
          (recur)
          (.commitSync consumer))))
    ))

(deftest lazy-test-json-post-spec

  (testing "loading from file"
    (= 3
       (-> (csv-file->xfrm test-file-location
                           (partial zipmap fixture-header-row))
            count)))

  (testing "loading from file and posting to kafka"
   (let [_ (poll-for-test-msgs)]
     (is (= (do (post-test-msgs-from-file)
                3)
          (count read-buffer))))

  (testing "posting json to kafka"
   (let [_ (poll-for-test-msgs)]
     (is (= (do (post-test-msgs)
                3)
          (count read-buffer)))))))

(use-fixtures :each reset-read-buffer)
