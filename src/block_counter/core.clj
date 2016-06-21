(ns block-counter.core
  (:gen-class)
  (:require
      [cheshire.core :refer :all :as json]
      [clj-http.lite.client :as http]))

(def capi-host (System/getenv "CAPI_HOST"))

(def search-url (str "https://"
    capi-host
    "/search"))

(def params {
    "tag" "tone/minutebyminute"
    "show-blocks" "all"
    "page-size" "100"
    })

(defn read-live-blogs []
    (let [result (http/get search-url {:query-params params :throw-exceptions false})
        body (:body result)
        content-data (parse-string body true)]
        content-data))

(defn read-blocks [content-data]
    (let [
        results (->> content-data :response :results)
        blocks (map (fn [item] (:blocks item)) results)
        block-counts (map (fn [block-list] (count (:body ))))]
    (println blocks)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
