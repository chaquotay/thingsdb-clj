(ns thingsdb-clj.html
  (:use [clojure.string :only [join]]))

(defn listpage [title list]
  (str "<html><head><title>" title "</title></head><body><ul>" (join (map listitem list)) "</ul></body></html>"))

(defn listitem [item]
  (str "<li><a href=\"" (:url item) "\">" (:title item) "</a> -- Tags: " (join ", " (:tags item)) " (ID: " #_{:_id item} ")</li>"))