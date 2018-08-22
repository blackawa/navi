(ns user
  (:refer-clojure :exclude [test])
  (:require [clojure.java.io :as io]
            [integrant.core :as ig]
            [integrant.repl :refer [clear go halt prep init reset reset-all set-prep!]]
            [eftest.runner :as eftest]
            [navi.app :as app]))

(defn test []
  (eftest/run-tests (eftest/find-tests "test")))

(let [config (app/config {:profile :dev})]
  (-> config
      constantly
      set-prep!)
  (ig/load-namespaces config))
