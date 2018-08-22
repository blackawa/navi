(ns navi.event.url-verification
  (:require [navi.util.response :as res]))

(defn verify [params]
  (res/ok (select-keys params [:challenge])))
