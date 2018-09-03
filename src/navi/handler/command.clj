(ns navi.handler.command
  (:require [clojure.core.async :refer [go >!]]
            [integrant.core :as ig]
            [navi.boundary.logger :refer [log]]
            [navi.boundary.rotation :as rotation]
            [navi.util.response :as res]))

(defmethod ig/init-key ::rotate
  [_ {:keys [db logger channel]
      {slack-client-id :client-id slack-client-secret :client-secret} :slack}]
  (fn [{{response-url :response_url
         team-id :team_id
         text :text
         :as params} :params :as req}]
    (log logger :info :receive-rotate-command params)
    (go (>! channel {:text text :response-url response-url :team-id team-id}))
    (res/ok)))
