(ns navi.component.rotate
  (:require [clojure.core.async :refer [chan close! go-loop <!]]
            [integrant.core :as ig]
            [navi.boundary.rotation :as rotation]))

(defmethod ig/init-key ::channel [_ _]
  (chan))

(defmethod ig/halt-key! ::channel [_ channel]
  (close! channel))

(defmulti dispatch-rotate (fn [[identifier] channel-id db] identifier))
(defmethod dispatch-rotate "create" [[_ rotation-name & candidates] channel-id db]
  ;; TODO: create / list / delete という名前のローテーションを作ろうとしたらエラー
  (let [error? (or (some #{rotation-name} ["create" "list" "delete"])
                   (rotation/find-by-name db rotation-name))]
    )
  ;; TODO: candidatesは全部ユーザー名もしくはユーザーグループ名でなければエラー
  ;; TODO: すでに登録済みの名前を指定されたらエラー
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )
(defmethod dispatch-rotate "list" [_ channel-id db]
  ;; TODO: 今登録されているすべてのrotationを表示する
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )
(defmethod dispatch-rotate "delete" [[_ rotation-name] channel-id db]
  ;; TODO: 指定されたローテーションがあれば消す
  ;; TODO: ローテーションがなければエラーを返す
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )
(defmethod dispatch-rotate :default [[rotation-name] channel-id db]
  ;; TODO: rotationを検索する。
  ;; TODO: あればそれをローテーションする。
  ;; TODO: 取得されたのがユーザーIDならメンションにする。
  ;; TODO: なければエラーレスポンスを返す。
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )

(defmethod ig/init-key :navi.component/rotate [_ {:keys [channel db]}]
  (go-loop []
    (when-let [{text :text channel-id :channel-id} (<! channel)]
      (println text))))
