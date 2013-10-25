(ns thingsdb-clj.html
  (:use [clojure.string :only [join]]
        [hiccup core page element]))

(defn page [title body]
  (html5
    [:head [:title title] (include-css "/css/bootstrap.min.css" "/css/justified-nav.css")
     [:body [:div.container [:div.masthead [:ul.nav.nav-justified [(if (= "Things" title) :li.active :li) [:a {:href "/pages/things"} "Things"]] [(if (= "Tags" title) :li.active :li) [:a {:href "/pages/tags"} "Tags"]]] [:br] body]]]]))

(defn listitem [item]
  [:li.list-group-item [:a {:href (:url item)} (:title item)] " - " (link-to "#todo" [:span.glyphicon.glyphicon-edit]) " - " (map #(list " " (link-to (str "/pages/tags/" %) [:span.badge %])) (:tags item))]
  )

(defn listsection [section items]
  [:div [:a {:name section}] [:h3 section] [:ul.list-group (map listitem items)]]
  )

(defn listsections [items]
  (let [sections (into (sorted-map) (group-by #(get-in % [:title 0]) items))]
    (concat [[:ul.pagination (map #(identity [:li [:a {:href (str "#" %)} %]])(keys sections))]] (map #(apply listsection %) (seq sections)))
    ))

(defn listpage [list]
  (page "Things" (listsections list)))

(defn tagitem [tag]
  [:li.list-group-item (link-to {:name (:_id tag)} (str "/pages/tags/" (:_id tag)) (:_id tag)) [:span.badge (int (get-in tag [:value :count]))]])

(defn tagsection [section tags]
  [:div [:a {:name section}] [:h3 section] [:ul.list-group (map tagitem (sort-by :_id tags))]]
  )

(defn tagsections [tags]
  (let [sections (into (sorted-map) (group-by #(.toUpperCase (str (get-in % [:_id 0]))) tags))]
    (concat [[:ul.pagination (map #(identity [:li [:a {:href (str "#" %)} %]])(keys sections))]] (map #(apply tagsection %) (seq sections)))
    ))

(defn tagpage [tags]
  (page "Tags" (tagsections tags)))

