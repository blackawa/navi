(ns navi.handler.command
  (:require [integrant.core :as ig]
            [navi.boundary.logger :refer [log]]
            [navi.boundary.rotation :as rotation]
            [navi.util.response :as res]))

(defmulti dispatch-rotate (fn [[identifier] channel-id] identifier))
(defmethod dispatch-rotate "create" [[_ rotation-name & candidates] channel-id]
  ;; TODO: create / list / delete という名前のローテーションを作ろうとしたらエラー
  (let [error? (or (some #{rotation-name} ["create" "list" "delete"])
                   )])
  ;; TODO: candidatesは全部ユーザー名もしくはユーザーグループ名でなければエラー
  ;; TODO: すでに登録済みの名前を指定されたらエラー
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )
(defmethod dispatch-rotate "list" [_ channel-id]
  ;; TODO: 今登録されているすべてのrotationを表示する
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )
(defmethod dispatch-rotate "delete" [[_ rotation-name] channel-id]
  ;; TODO: 指定されたローテーションがあれば消す
  ;; TODO: ローテーションがなければエラーを返す
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )
(defmethod dispatch-rotate :default [[rotation-name] channel-id]
  ;; TODO: rotationを検索する。
  ;; TODO: あればそれをローテーションする。
  ;; TODO: 取得されたのがユーザーIDならメンションにする。
  ;; TODO: なければエラーレスポンスを返す。
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )

(defmethod ig/init-key ::rotate
  [_ {:keys [db logger]
      {slack-client-id :client-id slack-client-secret :client-secret} :slack}]
  (fn [{{channel-id :channel_id
         team-id :team_id
         text :text
         :as params} :params :as req}]
    (log logger :info :receive-rotate-command params)
    (dispatch-rotate (clojure.string/split text) channel-id)
    (res/ok)))
