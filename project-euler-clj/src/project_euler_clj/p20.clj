(ns project-euler-clj.p20
  (:use clojure.test))

;; n! means n × (n - 1) * ... * 3 * 2 * 1

;; For example, 10! = 10 * 9 * ... * 3 * 2 * 1 = 3628800,
;; and the sum of the digits in the number 10! is 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.

;; Find the sum of the digits in the number 100!


;; C&P from p16

(defn digit-seq-pad-zero [ds left]
  (concat (take left (repeat 0)) ds))

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

(declare digit-seq-to-number)
(deftest test-digit-seq-to-number
  (is (= (digit-seq-to-number [1 2 3]) 123)))

;; digit-sequence를 number로 바꾼다.
;; 좋지 않아... overflow가 발생하면???
(defn digit-seq-to-number [ds]
  (reduce #(+ (* 10 %1) %2) ds))

;; test digit-seq-times
(declare digit-seq-times)
(deftest test-digit-seq-times
  (is (= (digit-seq-times [2] [2]) [4])))

;; digit-seq-times: 단순하게 n번 digit-seq-add 한다.
;; digit-seq 개념이 overflow 때문에 적용한건데, 이걸 다시 number로 바꾸면 뭔 의미가 있을까???
(defn digit-seq-times [ds1 ds2]
  (let [n (digit-seq-to-number ds2)]
    (reduce digit-seq-add (repeat n ds1))))

(declare digit-seq-factorial)
(deftest test-digit-seq-factorial
  (is (= (digit-seq-factorial 2) [2]))
  (is (= (digit-seq-factorial 10) [3 6 2 8 8 0 0])))

;; digit-seq factorial
(defn digit-seq-factorial [n]
  (reduce digit-seq-times (map vector (range 1 (+ n 1)))))

;;(reduce + (digit-seq-factorial 100))
;; => 648
