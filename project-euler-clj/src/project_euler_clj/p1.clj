(ns project-euler-clj.p1
  (:use clojure.test))

;; http://projecteuler.net

;; problem 1
;; If we list all the natural numbers below 10
;; that are multiples of 3 or 5, we get 3, 5, 6 and 9.
;; The sum of these multiples is 23.
;; Find the sum of all the multiples of 3 or 5 below 1000.

(defn p1 [n]
  (apply + (filter
           #(or (= (mod % 5) 0) (= (mod % 3) 0))
           (range n))))
;; (p1 1000) => 233168
