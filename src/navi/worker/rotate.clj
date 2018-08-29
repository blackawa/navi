(ns navi.worker.rotate
  (:require [clj-http.client :as http]
            [clojure.core.async :refer [chan close! go-loop <!]]
            [integrant.core :as ig]
            [navi.boundary.logger :refer [log]]
            [navi.boundary.rotation :as rotation]))

(defmulti dispatch-rotate (fn [{[identifier] :text}] identifier))

(defmethod dispatch-rotate "create" [{[_ rotation-name & candidates] :text :keys [response-url db logger]}]
  ;; TODO: bouncerでバリデーションして適切なエラーメッセージを取得する
  (let [error? (or (some #{rotation-name} ["create" "list" "delete" "help"])
                   (rotation/find-by-name db rotation-name)
                   (nil? candidates))]
    (if error?
      (http/post response-url {:form-params {:response_type "ephemeral"
                                             :text "ローテーションの作成に失敗しました。\n
create/list/delete/helpという名前のローテーションは作れません。\n
すでに同じ名前のローテーションがあります。\n
メンバーが足りません。"}
                               :content-type :json})
      (do
        ;; TODO: candidatesは全部ユーザー名もしくはユーザーグループ名でなければエラー
        (rotation/create db {:name rotation-name
                             :users candidates})
        (http/post response-url {:form-params {:text "ローテーションを作成しました。 `/rotate ローテーション名` で担当者にメンションが飛びます。"}
                                 :content-type :json})))))

(defmethod dispatch-rotate "list" [{:keys [response-url db logger]}]
  ;; TODO: 今登録されているすべてのrotationを表示する
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )

(defmethod dispatch-rotate "delete" [{[_ rotation-name] :text :keys [response-url db logger]}]
  ;; TODO: 指定されたローテーションがあれば消す
  ;; TODO: ローテーションがなければエラーを返す
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )

(defmethod dispatch-rotate :default [{[rotation-name] :text :keys [response-url db logger]}]
  ;; TODO: rotationを検索する。
  ;; TODO: あればそれをローテーションする。
  ;; TODO: 取得されたのがユーザーIDならメンションにする。
  ;; TODO: なければエラーレスポンスを返す。
  ;; TODO: delayed responseを使うようにする。すぐに返事をするとコマンド自体もチャンネルに共有されて邪魔なため。
  )

(defmethod ig/init-key :navi.worker/rotate [_ {:keys [channel db logger]}]
  (println "Starting :navi.worker/rotate =====================")
  (go-loop []
    (when-let [rotate-command-params (<! channel)]
      (log logger :info ::receive-data rotate-command-params)
      (dispatch-rotate (-> rotate-command-params
                           (merge {:db db :logger logger :channel channel})
                           (update :text clojure.string/split #" ")))
      (recur))))
