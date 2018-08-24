(ns navi.component.slack
  (:require [integrant.core :as ig]))

(defmethod ig/init-key :navi.component/slack [_ config]
  config)
