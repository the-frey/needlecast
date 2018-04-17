(defproject needlecast "0.1.0"
  :description "A tiny adaptor for Apache Kafka"
  :url "http://github.com/the-frey/needlecast"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.apache.kafka/kafka-clients "1.1.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [environ "1.1.0"]
                 [com.taoensso/nippy "2.13.0"]
                 [metosin/jsonista "0.1.1"]
                 [org.clojure/data.csv "0.1.4"]])
