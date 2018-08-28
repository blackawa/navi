(ns navi.handler.command
  (:require [integrant.core :as ig]
            [navi.boundary.logger :refer [log]]))

(defmethod ig/init-key ::assign [_ {:keys [db logger]}]
  (fn [{body-params :body-params :as req}]
    (log logger :info :receive-assign-command body-params)
    {:status 200}))
