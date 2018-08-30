(ns navi.validator.rotate
  (:require [bouncer.core :as b]
            [bouncer.validators :as v]
            [navi.boundary.rotation :as rotation]))

(def preserved-rotation-names ["create" "list" "delete" "help"])

(v/defvalidator rotation-name-is-not-preserved-words
  {:default-message-format (str "%sに" (clojure.string/join "/" preserved-rotation-names) "は使えません")}
  [rotation-name]
  (not (some (set preserved-rotation-names) #{rotation-name})))

(v/defvalidator rotation-name-is-not-already-used
  {:default-message-format "%sと同名のローテーションがもうあります"}
  [rotation-name db]
  (empty? (rotation/find-by-name db rotation-name)))

(v/defvalidator rotation-contains-more-than-two-users
  {:default-message-format "%sを2人以上指定してください"}
  [users]
  (>= (count users) 2))

(defn validate-new-rotation [entity db]
  (b/validate entity
              :name [[v/required :message "名前が必要です"]
                     rotation-name-is-not-preserved-words
                     [rotation-name-is-not-already-used db]]
              :users [rotation-contains-more-than-two-users
                      [v/every #(clojure.string/starts-with? % "@") :message "ユーザーへのメンションを指定してください"]]))
