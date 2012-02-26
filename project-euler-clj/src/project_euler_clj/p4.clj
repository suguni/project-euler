(ns project-euler-clj.p4
  (:use clojure.test))

;; http://projecteuler.net

;; problem 4
;; A palindromic number reads the same both ways.
;; The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
;; Find the largest palindrome made from the product of two 3-digit numbers.

;; from 100*100 to 999*999

(defn rotate-number [n]
  (Integer. (apply str (reverse (str n)))))

(defn palindromic-number? [n]
  (= (rotate-number n) n))

(deftest test-rotate-number
  (is (= (rotate-number 1234) 4321)))

(defn n-digit-max [n]
  (- (long (Math/pow 10 n)) 1))

(defn n-digit-min [n]
  (long (Math/pow 10 (- n 1))))

(deftest test-n-digit-max-min
  (is (= (n-digit-max 3) 999))
  (is (= (n-digit-max 1) 9))
  (is (= (n-digit-min 3) 100))
  (is (= (n-digit-min 1) 1)))

(defn p4 [n]
  (let [seq (range (n-digit-max n) (- (n-digit-min n) 1) -1)]
    (apply max
     (filter palindromic-number?
             (for [x seq y seq :when (<= x y)] (* x y))))))

;; (p4 3) => 906609
;; 이렇게 만들면 전체 seq를 모두 검사해야 한다.
;; 처음부터 순서대로 만들어지면 첫번째만 뽑아내면 되는데...
