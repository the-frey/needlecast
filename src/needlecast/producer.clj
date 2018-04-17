(ns needlecast.producer
  (:require [taoensso.nippy :as nippy]
            [jsonista.core :as json])
  (:import [org.apache.kafka.common.serialization ByteArraySerializer StringSerializer]
           [org.apache.kafka.clients.producer KafkaProducer ProducerRecord]))

(def string-properties-config
  {"key.serializer" StringSerializer
   "value.serializer" StringSerializer})

(def byte-array-properties-config
  {"key.serializer" ByteArraySerializer
   "value.serializer" ByteArraySerializer})

(defn send [kafka-producer topic msg]
  (.send kafka-producer (ProducerRecord. topic msg)))

(defn producer-from [properties]
  (KafkaProducer. properties))
