(ns navi.method.chat
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn post-message
  "see https://api.slack.com/methods/chat.postMessage"
  ([token channel text]
   (post-message token channel text {}))
  ([token channel text optional-params]
   (let [url "https://slack.com/api/chat.postMessage"
         params (merge {:token token :channel channel :text text} optional-params)
         {body :body} (http/post url {:form-params params :content-type :json})
         {ok? "ok" error "error" :as body} (json/parse-string body)]
     (when (not ok?)
       (throw (ex-info "Failed to post message."
                       {:causes :slack-returns-error
                        :error error
                        :body body}))))))
