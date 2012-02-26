(ns project-euler-clj.p5
  (:use clojure.test))

;; problem 5
;; 2520 is the smallest number that can be divided by each of the numbers
;; from 1 to 10 without any remainder.
;; What is the smallest positive number that is evenly divisible by
;; all of the numbers from 1 to 20?

;; lcm(a, b, ... , z) = product(a, b, ..., z) / gcd(a, b, ... , z)
;; n개 인자의 최소공배수를 찾는 문제. 아니다.

;; 1 to 20 까지 각 수의 prime-factor를 계산하고,
;; prime-factor들을 merge(계수가 가장 큰값으로)하여 계산한다.

(defn divide? [a b]
  (= (mod a b) 0))

(defn prime-factors [n]
  (letfn [(iter [a b seq]
            (cond
             (= a 1) seq
             (divide? a b) (recur (/ a b) b (conj seq b))
             :else (recur a (+ b 1) seq)))]
    (iter n 2 [])))

(deftest prime-factors-test
  (is (= (prime-factors 2) [2]))
  (is (= (prime-factors 8) [2 2 2]))
  (is (= (prime-factors 9) [3 3]))
  (is (= (prime-factors 21) [3 7])))

(defn coeff-count [seq]
  (reduce (fn [seq n]
            (merge-with + seq (hash-map n 1)))
          {} seq))

(deftest coeff-count-test
  (is (= (coeff-count [2]) {2 1}))
  (is (= (coeff-count [2 2 2]) {2 3}))
  (is (= (coeff-count [3 7]) {3 1, 7 1})))

(defn merge-with-max [seq]
  (apply merge-with max seq))

(defn product-pow [m]
  (long (reduce (fn [s v]
                  (* s (Math/pow (first v) (second v))))
                1 m)))

(deftest product-pow-test
  (is (= (product-pow {2 1, 3 2}) 18))
  (is (= (product-pow {7 1, 5 1, 3 2, 2 3}) 2520)))

(defn p5 [seq]
  (product-pow 
   (merge-with-max
     (map coeff-count
          (map prime-factors seq)))))

(deftest p5-test
  (is (= (p5 (range 1 11)) 2520))
  (is (= (p5 (range 1 21)) 232792560))) ;; solved!

;; prime-factors는 lazy-sequence를 반환하도록 하면 좋을거 같은데...
;; 풀이에는 상관 없나???
