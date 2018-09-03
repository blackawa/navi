(ns navi.method.users
  (:require [cheshire.core :as json]
            [clj-http.client :as http])
  (:refer-clojure :exclude [list]))

(defn list
  ([access-token]
   (list access-token nil))
  ([access-token cursor]
   (let [{:keys [body]}
         (http/get "https://slack.com/api/users.list"
                   {:headers {"Authorization" (str "Bearer " access-token)}
                    :params (-> {:limit 100}
                                #(if cursor (assoc % :cursor cursor) %))})
         {ok? "ok" members "members"
          {cursor "next_cursor"} "response_metadata"
          :as body} (json/parse-string body)]
     (if (not ok?)
       (throw (ex-info ::list-api-response-is-not-ok body))
       {:cursor cursor
        :members members}))))
