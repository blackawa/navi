(ns navi.component.http
  (:require [integrant.core :as ig]
            [ring.adapter.jetty :as jetty]
            [navi.boundary.logger :as logger]))

(defmethod ig/init-key :navi.component/http [_ {:keys [handler logger] :as opts}]
  ;; (logger/log logger :info ::http "Start http server on port" (:port opts))
  (jetty/run-jetty handler (-> opts (dissoc :handler) (assoc :join? false))))

(defmethod ig/halt-key! :navi.component/http [_ server]
  (.stop server))
