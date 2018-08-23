(ns navi.handler
  (:require [integrant.core :as ig]
            [navi.boundary.logger :as logger]
            [navi.event.message :as message]
            [navi.event.url-verification :as url-verification]
            [navi.util.response :as res]))

(defmethod ig/init-key ::create [_ {:keys [logger]}]
  ;; 全部のAPIコールを一度このエンドポイントで受ける
  ;; TODO: validate token
  (fn [{{:keys [type] {channel-type :channel_type event-type :type :as event} :event :as body-params} :body-params :as req}]
    (cond
      ;; URLチェック
      (= type "url_verification")
      (url-verification/verify body-params)
      ;; メッセージ受け取り
      (and (= type "event_callback")
           (= event-type "message")
           (= channel-type "app_home"))
      (message/app-home body-params)
      :else (logger/log logger :warn ::create {:message "No match" :channel-type channel-type :event-type event-type :type type}))
    (res/ok)))
