(ns navi.handler.oauth
  (:require [cemerick.url :refer [url]]
            [integrant.core :as ig]
            [rum.core :as rum]
            [navi.method.oauth :as oauth]
            [navi.util.response :as res]))

(rum/defc index-view [client-id]
  (let [scopes ["chat:write"]
        base-url (url "https://slack.com/oauth/authorize")
        query-params {:scope (clojure.string/join " " scopes)
                      :client_id client-id}
        oauth-request-url (-> base-url (assoc :query query-params) str)]
    [:html
     [:head]
     [:body
      [:section
       [:a {:href oauth-request-url}
        [:img {:alt "Add to Slack" :height "40" :width "139" :src "https://platform.slack-edge.com/img/add_to_slack.png" :srcset "https://platform.slack-edge.com/img/add_to_slack.png 1x, https://platform.slack-edge.com/img/add_to_slack@2x.png 2x"}]]]]]))

(defmethod ig/init-key ::index [_ {{client-id :client-id
                                    client-secret :client-secret}
                                   :slack}]
  (fn [req]
    (-> (res/ok (rum/render-html (index-view client-id))) res/html)))

(defmethod ig/init-key ::callback [_ {{client-id :client-id
                                       client-secret :client-secret}
                                      :slack
                                      :keys [db logger]}]
  (fn [{{code :code} :params :as res}]
    (res/ok (oauth/access db logger client-id client-secret code))))
