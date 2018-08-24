(ns navi.handler
  (:require [integrant.core :as ig]
            [navi.boundary.logger :as logger]
            [navi.event.message :as message]
            [navi.event.url-verification :as url-verification]
            [navi.util.response :as res]))

(defn- no-match [logger body-params]
  (logger/log logger :warn ::event-callback-no-match body-params)
  (res/ok))

(defn- dispatch-event-callback
  [db logger
   {{channel-type :channel_type event-type :type :as event} :event
    :as body-params}]
  (cond
    (and (= event-type "message")
         (= channel-type "app_home"))
    (message/app-home body-params)
    (and (= event-type "message")
         (= channel-type "channel"))
    (message/channels db body-params)
    :else (no-match logger body-params)))

(defmethod ig/init-key ::create [_ {:keys [logger db]}]
  ;; 全部のAPIコールを一度このエンドポイントで受ける
  ;; TODO: validate token
  (fn [{{:keys [type] :as body-params} :body-params :as req}]
    (logger/log logger :info :receive-event body-params)
    (condp = type
      ;; URLチェック
      "url_verification"
      (url-verification/verify body-params)
      ;; 通常のEvents APIコールバック
      "event_callback"
      (dispatch-event-callback db logger body-params)
      ;; 何にもマッチしないときのフォールバック
      (no-match logger body-params))))
