(ns navi.component.logger
  (:require [integrant.core :as ig]
            [taoensso.timbre :as timbre]))

(defrecord Logger [config])

(defmethod ig/init-key :navi.component/logger [_ options]
  (->Logger (assoc options :appenders {:println (timbre/println-appender)})))
