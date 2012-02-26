(ns project-euler-clj.p3
  (:use clojure.test))

;; http://projecteuler.net

;; problem 3
;; The prime factors of 13195 are 5, 7, 13 and 29.
;; What is the largest prime factor of the number 600851475143 ?

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

(defn p3 [n]
  (apply max (filter #(divide? n %) (filter prime? (range 2 n)))))

(defn find-divisor-2 [n d]
  (cond
   (< d 2) n
   (divide? n d) d
   :else (recur n (- d 1))))

(defn largest-divisor [n]
  (find-divisor-2 n (bigint (+ 0.5 (/ n 2)))))

(defn p3-2 [n]
  (largest-divisor n))

;; p3, p3-2는 너무 오래 걸려서 결과 확인 못함.


;; 인자의 약수가 모두 prime 이라고 가정하고,
;; 2부터 시작해서 약수를 찾으면 결과에 추가하고,
;; 약수로 나눠진 값을 찾은 약수 다음값부터 시작해서 다음번 약수를 찾는다.
;; prime 인지 검사하거나 prime을 찾는 함수는 필요 없다.
(defn p3-3 [n]
  (letfn [(iter [a b seq]
            (cond
             (> (* b b) a) (conj seq a)
             (divide? a b) (recur (/ a b) (+ b 1) (conj seq b))
             :else (recur a (+ b 1) seq)))]
    (apply max (iter n 2 []))))

;; (p3-3 600851475143) => 6857
