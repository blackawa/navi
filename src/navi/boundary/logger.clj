(ns navi.boundary.logger
  (:require [taoensso.timbre :as timbre]
            [navi.component.logger]))

(defprotocol TimbreLogger
  (log [this level message data]))

(extend-protocol TimbreLogger
  navi.component.logger.Logger
  (log [this level message data]
    (timbre/log* (:config this) level message data)))
