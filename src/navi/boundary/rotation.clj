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
                             (jdbc/insert! tran :rotations)
                             first
                             :id)
            first-rotation-user-id (->> users
                                        ;; TODO: userのdisplay nameからuserIdを引く
                                        (map #(assoc {} :slack_user_id %))
                                        (map #(assoc % :rotation_id rotation-id))
                                        (map-indexed #(assoc %2 :sort %1))
                                        (jdbc/insert-multi! tran :rotation_users)
                                        first
                                        :id)]
        (->> {:rotation_id rotation-id :rotation_user_id first-rotation-user-id}
             (jdbc/insert! tran :rotation_currents))))))
