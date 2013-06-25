(ns project-euler-clj.p22
  (:use clojure.test)
  (:require [clojure.string :as str]))

;; Using names.txt (right click and 'Save Link/Target As...'), a 46K
;; text file containing over five-thousand first names, begin by
;; sorting it into alphabetical order. Then working out the
;; alphabetical value for each name, multiply this value by its
;; alphabetical position in the list to obtain a name score.
;; For example, when the list is sorted into alphabetical order,
;; COLIN, which is worth 3 + 15 + 12 + 9 + 14 = 53, is
;; the 938th name in the list. So, COLIN would obtain a score of
;; 938 × 53 = 49714.
;; What is the total of all the name scores in the file?

(def text (slurp "src/project_euler_clj/p22-names.txt"))

(def data (map #(str/replace % #"\"" "") (str/split text #",")))

(defn alpha-order [c]
  (+ 1 (- (int c) (int \A))))

(defn string->int [s]
  (reduce #(conj %1 (alpha-order %2)) [] s))

(defn name-score [name]
  (apply + (string->int name)))

(defn p22 []
  (apply +
         (map #(apply * %)
              (apply assoc {}
                     (interleave
                      (iterate inc 1)
                      (map name-score (sort data)))))))
