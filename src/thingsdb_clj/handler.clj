(ns thingsdb-clj.handler
  (:use compojure.core thingsdb-clj.mongodb thingsdb-clj.html [clojure.walk :only [keywordize-keys]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.json :as middleware]))

(connect)

(defroutes app-routes
  (context "/things" [] (defroutes things-routes
                          (GET  "/" [] (get-all-things))
                          (GET  "/add" {params :params} (insert-thing (select-keys params [:title :url :text :tags])))
                          ;;(GET  "/add" {params :params} (insert-thing (select-keys (keywordize-keys params) [:title :url :text :tags])))
                          (GET  "/list.html" [] (listpage "Things" (filter :url (get-all-things))))
                          (GET  "/index.html" [] (resp/resource-response "things.html" {:root "public"}))
                          (POST "/" {body :body} (insert-thing body))
                          (context "/thing/:id" [id] (defroutes thing-routes
                                                       (GET    "/" [] (get-thing id))
                                                       (PUT    "/" {body :body} (update-thing id body))
                                                       (DELETE "/" [] (remove-thing id))))))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)))
