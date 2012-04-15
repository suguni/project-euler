(ns project-euler-clj.p16
  (:use clojure.test))

;; 2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
;; What is the sum of the digits of the number 2^1000?

(defn digit-seq-pad-zero [ds left]
  (concat (take left (repeat 0)) ds))

(deftest test-digit-seq-pad-zero
  (is (= (digit-seq-pad-zero [1] 4)) [0 0 0 0 1])
  (is (= (digit-seq-pad-zero [] 4)) [0 0 0 0]))


;; bigint를 사용하더라도 2^1000가 더 크므로 정수를 sequence로 표현하여 덧샘을 구현한다.
;; 근데, 아래 코드는 넘 지저분... -_-;;;
(defn digit-seq-add [ds1 ds2]
  (let [diff (- (count ds1) (count ds2))
        s1 (if (> diff 0) ds1 (digit-seq-pad-zero ds1 diff))
        s2 (if (> diff 0) (digit-seq-pad-zero ds2 diff) ds2)
        seq (reverse (map + s1 s2))
        ret (reduce (fn [m a]
                      (let [a (if (:f m) (+ a 1) a)
                            v (mod a 10)]
                        { :v (conj (:v m) v) :f (> a 9) }))
                    { :v [] :f false } seq)]
    (if (:f ret) (reverse (conj (:v ret) 1))
        (reverse (:v ret)))))

(deftest test-digit-seq-add
  (is (= (digit-seq-add [9 2] [8]) [1 0 0]))
  (is (= (digit-seq-add [9 8 7] [1]) [9 8 8]))
  (is (= (digit-seq-add [9 2 2] [8 8]) [1 0 1 0]))
  (is (= (digit-seq-add [2] [2]) [4]))
  (is (= (digit-seq-add [1 6] [1 6]) [3 2])))

(defn digit-seq-double [ds]
  (digit-seq-add ds ds))

(deftest test-digit-seq-double
  (is (= (digit-seq-double [2]) [4]))
  (is (= (digit-seq-double [6 4]) [1 2 8])))

(defn digit-seq-pow2 [n]
  (loop [v [2]
         n n]
    (if (= n 1) v
        (recur (digit-seq-double v) (dec n)))))

;; (def solution (reduce + 0 (digit-seq-pow2 1000)))
