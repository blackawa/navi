(ns navi.boundary.workspace
  (:require [clojure.java.jdbc :as jdbc]
            [duct.database.sql]
            [honeysql.core :as sql]))

(defprotocol Workspace
  (create [this entity])
  (find-by-team-id [this team-id]))

(extend-protocol Workspace
  duct.database.sql.Boundary
  (create [{db :spec} {:keys [team-id access-token app-user-id]}]
    (if-let [existing-workspace (->> {:select [:*] :from [:workspaces] :where [:= :team_id team-id]}
                                     sql/format
                                     (jdbc/query db)
                                     first)]
      (do
        (->> {:update :workspaces :set [[:access_token access-token]
                                        [:app_user_id app-user-id]]}
             sql/format
             (jdbc/execute! db))
        (->> {:select [:*] :from [:workspaces] :where [:= :team_id team-id]}
             sql/format
             (jdbc/query db)
             first))
      (->> {:team_id team-id :access_token access-token :app_user_id app-user-id}
           (jdbc/insert! db :workspaces)
           first)))
  (find-by-team-id [{db :spec} team-id]
    (->> {:select [:*]
          :from [:workspaces]
          :where [:= :team_id team-id]}
         sql/format
         (jdbc/query db)
         first)))
