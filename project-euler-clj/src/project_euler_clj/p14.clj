; The following iterative sequence is defined for the set of positive integers:
;   n → n/2 (n is even)
;   n → 3n + 1 (n is odd)
; Using the rule above and starting with 13, we generate the following sequence:
;   13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1
; It can be seen that this sequence (starting at 13 and
;	finishing at 1) contains 10 terms. Although it has not been proved yet
;   (Collatz Problem), it is thought that all starting numbers finish at 1.
; Which starting number, under one million, produces the longest chain?
; NOTE: Once the chain starts the terms are allowed to go above one million.

(ns project-euler-clj.p14
  (:use clojure.test))

(defn exp-even [n]
  (/ n 2))

(defn exp-odd [n]
  (+ (* 3 n) 1))

(defn exp [n]
  (if (even? n)
    (exp-even n)
    (exp-odd n)))

(defn exp-seq [n]
  (take-while (complement #(= 1 %)) (iterate exp n)))

(defn run-seq [r]
  (reduce (fn [a b] (if (> (:count a) (:count b)) a b))
          {:value 1 :count 1}
          (map (fn [n] {:value n :count (+ 1 (count (exp-seq n)))})
               (range 1 (+ r 1)))))

;; (def answer (:value (run-seq 1000000)))
