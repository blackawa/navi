(ns navi.boundary.workspace
  (:require [clojure.java.jdbc :as jdbc]
            [duct.database.sql]
            [honeysql.core :as sql]))

(defprotocol Workspace
  (create [this entity])
  (find-by-team-id [this team-id]))

(extend-protocol Workspace
  duct.database.sql.Boundary
  (create [{db :spec} {:keys [team-id access-token]}]
    (first (jdbc/insert! db :workspaces {:team_id team-id :access_token access-token})))
  (find-by-team-id [{db :spec} team-id]
    (->> {:select [:*]
          :from [:workspaces]
          :where [:= :team_id team-id]}
         sql/format
         (jdbc/query db)
         first)))
