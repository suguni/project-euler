(ns project-euler-clj.p9
  (:use clojure.test))

;; Problem 9

;; A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
;; a^2 + b^2 = c^2

;; For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2.

;; There exists exactly one Pythagorean triplet for which a + b + c = 1000.
;; Find the product abc.

(defn pythagorean-triplet? [a b c]
  (= (+ (* a a) (* b b)) (* c c)))

(defn sum-thousand? [[a b c]]
  (= (+ a b c) 1000))

(defn next-triplet [a b c]
  (if (= (inc a) b)
    (if (= (inc b) c)
      [1 2 (inc c)]
      [1 (inc b) c])
    [(inc a) b c]))

(deftest test-next-triplet
  (is (= (next-triplet 1 2 3) [1 2 4]))
  (is (= (next-triplet 1 2 4) [1 3 4]))
  (is (= (next-triplet 1 3 4) [2 3 4]))
  (is (= (next-triplet 5 12 13) [6 12 13])))

(defn pythagorean-triplets []
  (letfn [(func [[a b c]]
            (if (pythagorean-triplet? a b c)
              (lazy-seq
               (cons [a b c]
                     (func (next-triplet a b c))))
              (recur (next-triplet a b c))))]
    (func [1 2 3])))

(defn sum-thousand-pythagorean-triplet []
  (first (filter sum-thousand? (pythagorean-triplets))))

(deftest test-sum-thousand-pythagorean-triplet
  (is (= (sum-thousand-pythagorean-triplet) [200 375 425])))

(defn p9 []
  (reduce * 1 (sum-thousand-pythagorean-triplet)))

;; (p9) => 31875000