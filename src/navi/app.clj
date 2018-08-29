(ns navi.app
  (:gen-class)
  (:require [aero.core :as aero]
            [clojure.java.io :as io]
            [integrant.core :as ig]))

(defmethod aero/reader 'ig/ref [_ _ value]
  (ig/ref value))

(defmethod aero/reader 'migration [_ _ value]
  ;; migrationファイルのup/down定義がさすがに冗長すぎたので生成する.
  ;; こういうことを続けるといつの間にかImplicitなシステムになっていくので塩梅は気をつける.
  (let [up-queries (->> value (map #(str % ".up.sql")) (map io/resource))
        down-queries (->> value (map #(str % ".down.sql")) (map io/resource))]
    {:up up-queries :down down-queries}))

(defmethod aero/reader 'regex [_ _ value]
  (re-pattern value))

(defn config
  ([] (config {}))
  ([opts] (aero/read-config (io/resource "config.edn") opts)))

(defn -main
  [& args]
  (let [config (config)
        keys (map keyword (or args [:navi/app]))]
    (println "load namespaces:" (ig/load-namespaces config))
    ;; Migrate for heroku
    (ig/init config [:duct.migrator/ragtime])
    (ig/init config keys)))
