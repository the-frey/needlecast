(ns needlecast.consumer
  (:require [taoensso.nippy :as nippy]
            [jsonista.core :as json])
  (:import [org.apache.kafka.common.serialization ByteArrayDeserializer StringDeserializer]
           [org.apache.kafka.clients.producer KafkaConsumer]))

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

(defn consumer-from [properties topic]
  (doto (KafkaConsumer. properties)
        (.subscribe topic)))
