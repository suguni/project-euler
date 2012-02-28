(ns project-euler-clj.p7
  (:use clojure.test))

;; problem 7
;;By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
;; What is the 10001st prime number?

(defn divide? [a b]
  (= (mod a b) 0))

(defn find-divisor [n d]
  (cond
   (> (* d d) n) n
   (divide? n d) d
   :else (recur n (+ d 1))))

(defn smallest-divisor [n]
  (find-divisor n 2))

(defn prime? [n]
  (= (smallest-divisor n) n))

(defn next-prime [n]
  (first (filter prime? (iterate inc (+ n 1)))))

(defn p7 [n]
  (nth (iterate next-prime 1) n))

(deftest test-p7
  (is (= (p7 1) 2))
  (is (= (p7 2) 3))
  (is (= (p7 3) 5))
  (is (= (p7 4) 7))
  (is (= (p7 5) 11))
  (is (= (p7 6) 13))
  (is (= (p7 10001) 104743)))
