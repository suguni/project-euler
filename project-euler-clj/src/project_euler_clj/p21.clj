(ns project-euler-clj.p21
  (:use clojure.test
        clojure.set))

;; d is sum of proper divisors
;; d(220) = 1 + 2 + 4 + 5 + 10 + 11 + 20 + 22 + 44 + 55 + 110 = 284
;; amicable number : a, b where d(a) = b and d(b) = a

(defn p21 [n]
  (apply + (reduce #(clojure.set/union %1 %2)
                   (map amicable-pair (range 1 n)))))

(defn amicable-pairs [n]
  (reduce #(clojure.set/union %1 %2)
          (map amicable-pair (range 1 n))))

(defn amicable-pair [a]
  (let [b (sum-divisors a)
        c (sum-divisors b)]
    (if (and (= a c) (not (= a b)))
      (set [a b])
      (set []))))

(defn sum-divisors [n]
  (apply + (divisors n)))

(defn divisors [n]
  (filter #(= (mod n %) 0) (range 1 n)))
