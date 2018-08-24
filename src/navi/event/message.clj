(ns navi.event.message
  (:require [navi.boundary.workspace :as workspace]
            [navi.method.chat :as chat]))

(defn app-home [{token :token {channel :channel text :text user :user} :event}]
  (chat/post-message token channel (str text "って言いました？")))

(defn channels [db {team-id :team-id
                    {channel :channel text :text user :user} :event}]
  (let [{token :access_token} (workspace/find-by-team-id db team-id)]
    (chat/post-message token channel (str "@<" user "> 「" text "」って言いました？"))))
