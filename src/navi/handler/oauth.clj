(ns navi.handler.oauth
  (:require [integrant.core :as ig]
            [rum.core :as rum]
            [navi.util.response :as res]))

(rum/defc index-view []
  [:html
   [:head]
   [:body
    [:section
     [:a {:href "https://slack.com/oauth/authorize?scope=bot&client_id=224069681875.420226130256"}
      [:img {:alt "Add to Slack" :height "40" :width "139" :src "https://platform.slack-edge.com/img/add_to_slack.png" :srcset "https://platform.slack-edge.com/img/add_to_slack.png 1x, https://platform.slack-edge.com/img/add_to_slack@2x.png 2x"}]]]]])

(defmethod ig/init-key ::index [_ _]
  (fn [req]
    (-> (res/ok (rum/render-html (index-view)))
        res/html)))

(defmethod ig/init-key ::callback [_ _]
  (fn [req]
    ))
