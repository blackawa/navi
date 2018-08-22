(ns navi.component.endpoint
  (:require [bidi.ring :refer [make-handler]]
            [integrant.core :as ig]))

(defmethod ig/init-key :navi.component/endpoint [_ {:keys [handlers routes middleware]}]
  (-> routes
      (make-handler #(handlers %))
      middleware))
