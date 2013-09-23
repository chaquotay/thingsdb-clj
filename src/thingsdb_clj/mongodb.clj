(ns thingsdb-clj.mongodb
  (:use [clojure.string :only [split blank?]])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]
           [java.util Date])
  )

(defn split-tags [tags]
  (if (blank? tags) [] (split tags #"\s+")))

(def things-db "things")
(def things-collection "things")

(defn connect [] (mg/connect!) (mg/set-db! (mg/get-db things-db)))

(defn disconnect [] (mg/disconnect!))

(defn insert-thing
  [thing]
  (let [id (ObjectId.)]
    (mc/insert things-collection (assoc thing :_id id :created (str (Date.)) :tags (split-tags (:tags thing))))
    (str "Created: " id)))

(defn get-thing [thing-id]
  (str (mc/find-map-by-id things-collection (ObjectId. thing-id))))

(defn remove-thing [thing-id]
  (mc/remove-by-id things-collection (ObjectId. thing-id)))

(defn update-thing [thing-id thing]
  (mc/update-by-id things-collection (ObjectId. thing-id) thing))

(defn get-all-things [] (mc/find-maps things-collection))