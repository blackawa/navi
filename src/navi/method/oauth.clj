(ns navi.method.oauth
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [navi.boundary.logger :as logger]
            [navi.boundary.workspace :as workspace]))

(defn access [db logger client-id client-secret code]
  (let [url "https://slack.com/api/oauth.access"
        form-params {:client_id client-id :client_secret client-secret :code code}
        {body :body} (http/post url {:form-params form-params})
        {ok? "ok"
         error "error"
         access-token "access_token"
         team-id "team_id"
         scopes "scopes"
         :as res} (json/parse-string body)]
    (if (not ok?)
      (throw (ex-info "Failed to acquire access token"
                      {:causes :slack-returns-error
                       :error error
                       :request-params form-params
                       :body res}))
      (do
        (logger/log logger :info :acquire-access-token res)
        (workspace/create db {:team-id team-id :access-token access-token})))))
