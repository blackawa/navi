(ns navi.component.nrepl
  (:require [clojure.tools.nrepl.server :as nrepl]
            [integrant.core :as ig]
            [navi.boundary.logger :as logger]))

(defmethod ig/init-key :navi.component/nrepl [_ {:keys [logger port]}]
  ;; Webアプリ用のポートとかち合わないようにする
  (let [default-port 7888
        port (if (= default-port (when port (read-string port))) (+1 default-port) default-port)]
    ;; (logger/log logger :info :navi.component/nrepl "Start nREPL server on port" port)
    (nrepl/start-server :port port)))

(defmethod ig/halt-key! :navi.component/nrepl [_ server]
  (nrepl/stop-server server))
