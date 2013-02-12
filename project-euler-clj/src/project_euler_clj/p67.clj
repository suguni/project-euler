(ns project-euler-clj.p67
  (:require [clojure.string :as str]))


(def data-source (slurp "src/project_euler_clj/p67-triangle.txt"))

(def data (map #(map (fn [s] (Integer. s)) (str/split % #"\s"))
               (str/split-lines data-source)))

(defn next-row [r1 r2]
  (map (fn [u b] (Math/max (+ u (first b)) (+ u (second b))))
       r1 (partition 2 1 r2)))

(defn p67 [tri]
  (reduce (fn [acc row] (next-row row acc)) (reverse tri)))
