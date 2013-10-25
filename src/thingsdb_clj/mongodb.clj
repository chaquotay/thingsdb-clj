(ns thingsdb-clj.mongodb
  (:use [clojure.string :only [split blank?]]
        [monger.conversion :only [from-db-object]])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern MapReduceCommand$OutputType MapReduceOutput DBObject]
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
    ;; (mc/insert things-collection (assoc thing :_id id :created (str (Date.)) :tags (split-tags (:tags thing))))
    (mc/insert things-collection (assoc (update-in thing [:tags] split-tags) :_id id :created (str (Date.))))
    (str "alert('Created: " id "');")))

(defn get-thing [thing-id]
  (str (mc/find-map-by-id things-collection (ObjectId. thing-id))))

(defn remove-thing [thing-id]
  (mc/remove-by-id things-collection (ObjectId. thing-id)))

(defn update-thing [thing-id thing]
  (mc/update-by-id things-collection (ObjectId. thing-id) thing))

(defn get-all-things [] (mc/find-maps things-collection))

(def tag-map "function Map() {
	if(this.tags) {
		for(var i=0; i<this.tags.length; i++) {
			var tag = this.tags[i];
			emit(tag, {things: [this], count: 1});
		}
	}
}")

(def tag-reduce "function Reduce(key, values) {
	var reduced = {things:[], count: 0};

	values.forEach(function(val) {
		reduced.things.push(val.things[0]);
		reduced.count++;
	});

	return reduced;
}")

(defn get-all-tags []
  (from-db-object (.results (mc/map-reduce things-collection tag-map tag-reduce nil MapReduceCommand$OutputType/INLINE {})) true))

(defn get-things-by-tag [tag]
  (mc/find-maps things-collection {:tags tag}))