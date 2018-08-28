(ns navi.handler.command
  (:require [integrant.core :as ig]
            [navi.boundary.logger :refer [log]]))

(defmethod ig/init-key ::assign [_ {:keys [db logger]}]
  (fn [{{channel-id :channel_id
         team-id :team_id
         text :text} :params :as req}]
    (log logger :info :receive-assign-command params)
    ;; TODO: 今までのアサイン履歴から次の担当者を決める
    ;; TODO: 新しいアサイン履歴を保存する
    {:status 200}))
