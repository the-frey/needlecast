(ns needlecast.core
  (:require [environ.core :as env]))

(defn get-producer-config-from-env []
  (:producer-config env/env))

(defn get-consumer-config-from-env []
  (:consumer-config env/env))
