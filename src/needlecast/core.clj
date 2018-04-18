(ns needlecast.core
  (:require [environ.core :as env]))

(defn get-producer-config-from-env []
  {"bootstrap.servers" (:bootstrap-servers env/env)})

(defn get-consumer-config-from-env []
  {"bootstrap.servers" (:bootstrap-servers env/env)})
