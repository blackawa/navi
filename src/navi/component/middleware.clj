(ns navi.component.middleware
  (:require [integrant.core :as ig]
            [muuntaja.middleware :refer [wrap-format]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [navi.boundary.logger :as logger]))

(defn wrap-log [handler logger]
  (fn [req]
    (logger/log logger :debug ::receive-request req)
    (try
      (handler req)
      (catch Exception e
        (logger/log logger :fatal ::unexpected-exception {:exception e})
        (throw e)))))

(defmethod ig/init-key :navi.component/middleware [_ {:keys [logger dev?]}]
  (fn [handler] (-> handler
                    (wrap-log logger)
                    wrap-format
                    (wrap-defaults api-defaults))))
