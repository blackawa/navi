(ns navi.handler
  (:require [integrant.core :as ig]
            [navi.boundary.logger :as logger]
            [navi.event.url-verification :as url-verification]))

(defmethod ig/init-key ::create [_ {:keys [logger]}]
  ;; 全部のAPIコールを一度このエンドポイントで受ける
  (fn [{:keys [body-params] :as req}]
    (condp = (:type body-params)
      "url_verification" (url-verification/verify body-params))))
