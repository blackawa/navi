(ns navi.component.routes
  (:require [integrant.core :as ig]))

(defmethod ig/init-key :navi.component/routes [_ routes]
  routes)
