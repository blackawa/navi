(defproject navi "1.0.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                  [org.clojure/core.async "0.4.474"]
                 ;; component lifecycle management
                 [aero "1.1.3"]
                 [integrant "0.6.3"]
                 ;; AP server
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [metosin/muuntaja "0.5.0"]
                 ;; database
                 [duct/database.sql.hikaricp "0.3.3"]
                 [duct/migrator.ragtime "0.2.2"]
                 [honeysql "0.9.2"]
                 [org.clojure/java.jdbc "0.7.6"]
                 [org.postgresql/postgresql "42.2.2"]
                 ;; others
                 [bidi "2.1.3"]
                 [bouncer "1.0.1"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.clojure/tools.nrepl "0.2.13"]
                 [clj-http "3.9.1"]
                 [cheshire "5.8.0"]
                 [rum "0.11.2"]
                 [com.cemerick/url "0.1.1"]]
  :main ^:skip-aot navi.app
  :uberjar-name "navi.jar"
  :plugins [[lein-heroku "0.5.3"]]
  :heroku {:app-name "boiling-stream-79705"}
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[integrant/repl "0.3.1"]
                                  [eftest "0.5.2"]
                                  [ring/ring-mock "0.3.2"]
                                  [com.gearswithingears/shrubbery "0.4.1"]]
                   :source-paths ["dev/src" "src"]
                   :repl-options {:init-ns user
                                  :host "0.0.0.0"
                                  :port 43509}}})
