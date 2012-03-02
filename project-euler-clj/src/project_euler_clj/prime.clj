(ns project-euler-clj.prime)

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

(defn primes-below [n]
  (take-while #(< % n) (iterate next-prime 2)))
