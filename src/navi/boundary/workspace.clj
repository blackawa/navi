(ns navi.boundary.workspace
  (:require [clojure.java.jdbc :as jdbc]
            [duct.database.sql]))

(defn create-workspace [db team-id access-token]
  (-> (jdbc/insert! db :workspaces {:team_id team-id :access_token access-token})
      first))

(defprotocol Workspace
  (create [this entity]))

(extend-protocol Workspace
  duct.database.sql.Boundary
  (create [{db :spec} entity]
    (create-workspace db (:team-id entity) (:access-token entity))))
