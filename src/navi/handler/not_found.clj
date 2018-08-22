(ns navi.handler.not-found
  (:require [integrant.core :as ig]))

(defmethod ig/init-key ::index [_ _]
  (fn [_] {:status 404 :body {:message "No resource found."}}))
