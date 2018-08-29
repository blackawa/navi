(ns navi.component.http
  (:require [integrant.core :as ig]
            [ring.adapter.jetty :as jetty]
            [navi.boundary.logger :refer [log]]))

(defmethod ig/init-key :navi.component/http [_ {:keys [handler logger] :as opts}]
  (log logger :info ::start opts)
  (jetty/run-jetty handler (-> opts (dissoc :handler) (assoc :join? false))))

(defmethod ig/halt-key! :navi.component/http [_ server]
  (.stop server))
