(ns needlecast.consumer
  (:require [taoensso.nippy :as nippy]
            [jsonista.core :as json])
  (:import [org.apache.kafka.common.serialization ByteArrayDeserializer StringDeserializer]
           [org.apache.kafka.clients.consumer KafkaConsumer]))

(def string-properties-config
  {"key.deserializer" StringDeserializer
   "value.deserializer" StringDeserializer})

(def byte-array-properties-config
  {"key.deserializer" ByteArrayDeserializer
   "value.deserializer" ByteArrayDeserializer})

(defn get-record-value [record]
  (-> record
      (.value)))

(defn read-json-record [record]
  (-> (get-record-value record)
      json/read-value))

(defn read-bytearray-record [record]
  (-> (get-record-value record)
      nippy/thaw))

(defn consumer-from [properties topics]
  (doto (KafkaConsumer. properties)
    (.subscribe topics)))

(defn lazy-poll! [consumer]
  "Returns a lazy seq of messages from consumer"
  (lazy-seq
   (let [records (.poll consumer 100)]
     (concat records (lazy-poll! consumer)))))

(defn lazy-poll->xfrm! [consumer xfrm-fn]
  "Returns a lazy seq of messages from consumer"
  (lazy-seq
   (let [records (.poll consumer 100)]
     (concat records (lazy-poll! consumer xfrm-fn)))))
