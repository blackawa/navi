(ns navi.channel.rotate
  (:require [clojure.core.async :refer [chan close!]]
            [integrant.core :as ig]))

(defmethod ig/init-key :navi.channel/rotate [_ _]
  (println "Starting :navi.channel/rotate =======================")
  (chan))

(defmethod ig/halt-key! :navi.channel/rotate [_ channel]
  (close! channel))
