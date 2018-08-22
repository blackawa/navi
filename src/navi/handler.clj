(ns navi.handler
  (:require [integrant.core :as ig]
            [navi.boundary.logger :as logger]))

(defmethod ig/init-key ::create [_ {:keys [logger]}]
  ;; 全部のAPIコールを一度このエンドポイントで受ける
  (fn [req]
    (logger/log logger :info ::receive-request (str req))
    {:status 200 :body "Hoge"}))
