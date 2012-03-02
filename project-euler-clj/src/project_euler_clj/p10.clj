(ns project-euler-clj.p10
  (:use clojure.test)
  (:use project-euler-clj.prime))

;; The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
;; Find the sum of all the primes below two million.

(defn p10 [n]
  (reduce + 0 (primes-below n)))

(deftest test-p10
  (is (= (p10 10) 17))
  (is (= (p10 2000000) 142913828922)))

