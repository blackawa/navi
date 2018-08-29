(ns navi.boundary.rotation
  (:require [clojure.java.jdbc :as jdbc]
            [duct.database.sql]
            [honeysql.core :as sql]))

(defprotocol Rotation
  (find-by-name [this name])
  (create [this entity]))

(extend-protocol Rotation
  duct.database.sql.Boundary
  (find-by-name [{db :spec} name]
    (->> {:select [:*]
          :from [:rotations]
          :where [:= :name name]}
         sql/format
         (jdbc/query db)
         first))
  (create [{db :spec} {rotation-name :name users :users}]
    (jdbc/with-db-transaction [tran db]
      (let [rotation-id (->> {:name rotation-name}
                             (jdbc/insert! db :rotations)
                             first
                             :id)]
        (->> users
             ;; TODO: userのdisplay nameからuserIdを引くのはかなり重い処理になるので、そもそもこの関数自体core.asyncごしに実行したほうがよさそう
             (map #(assoc {} :slack_user_id %))
             (map #(assoc % :rotation_id rotation-id))
             (jdbc/insert-multi! db :rotation_users))))))
