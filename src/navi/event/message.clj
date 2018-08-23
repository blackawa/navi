(ns navi.event.message
  (:require [navi.method.chat :as chat]))

(defn app-home [{token :token {channel :channel text :text user :user} :event}]
  (chat/post-message token channel (str text "って言いました？")))
