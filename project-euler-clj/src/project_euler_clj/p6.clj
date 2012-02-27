(ns project-euler-clj.p6
  (:use clojure.test))

;; The sum of the squares of the first ten natural numbers is,
;; 1^2 + 2^2 + ... + 10^2 = 385

;; The square of the sum of the first ten natural numbers is,
;; (1 + 2 + ... + 10)^2 = 55^2 = 3025

;; Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 − 385 = 2640.

;; Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.

(defn square [x]
  (* x x))

(defn square-sum [n]
  (reduce
   +
   (map square (range 1 (+ n 1)))))

(deftest test-square-sum
  (is (= (square-sum 10) 385))
  (is (= (square-sum 0) 0)))

(defn sum-square [n]
  (square (reduce + (range 1 (+ n 1)))))

(deftest test-sum-square
  (is (= (sum-square 10) 3025)))

(defn p6 [n]
  (- (sum-square n) (square-sum n)))

(deftest test-p6
  (is (= (p6 10) 2640))
  (is (= (p6 100) 25164150)))
