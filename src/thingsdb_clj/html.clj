(ns thingsdb-clj.html
  (:use [clojure.string :only [join]]))

(defn listitem [item]
  (str "<li><a href=\"" (:url item) "\">" (:title item) "</a> -- Tags: " (join ", " (:tags item)) " (ID: " (:_id item) ")</li>"))

(defn listpage [title list]
  (str "<html><head><title>" title "</title></head><body><ul>" (join (map listitem list)) "</ul></body></html>"))

(defn tagitem [tag]
  (str "<li>" (:_id tag) " #=> " (get-in tag [:value :count]) "</li>"))

(defn tagpage [title tags]
  (str "<html><head><title>" title "</title></head><body><ul>" (join (map tagitem (sort-by #(get-in % [:value :count]) > tags))) "</ul></body></html>"))