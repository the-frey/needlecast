(ns needlecast.io
  (:require [jsonista.core :as json]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn csv-data->maps [csv-data]
  (map zipmap
       (->> (first csv-data)
            (map keyword)
            repeat)
    (rest csv-data)))

(defn csv-file->maps [file-path]
  "If you prefer to chain together lots of map operations..."
  (with-open [reader (io/reader file-path)]
    (->> (csv/read-csv reader)
         csv-data->maps)))

(defn csv-file->xfrm [file-path xfrm-fn]
  "Takes a transform that operates on the each line of the csv.
   Drops the header."
  (with-open [reader (io/reader file-path)]
    (->> (csv/read-csv reader)
         (drop 1)
         (map xfrm-fn))))

(defn input-file->xfrm [file-path xfrm-fn]
  "Takes a transform that operates on a single line"
  (with-open [reader (io/reader file-path)]
    (doseq [line (line-seq reader)]
      (xfrm-fn line))))