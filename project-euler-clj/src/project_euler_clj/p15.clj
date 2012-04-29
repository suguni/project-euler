(ns project-euler-clj.p15
  (:use clojure.test))

;; Starting in the top left corner of a 2×2 grid,
;; there are 6 routes (without backtracking) to the bottom right corner.
;; How many routes are there through a 20×20 grid?

;; tracing 이 아닌 추론으로 방법을 찾음.
;; grid 수가 증가함에 따라 한변 각 교점에서의 이동 가지수는 아래와 같은 순열이 나오므로,
;; 아래 순열을 모두 더한 후 2배하면 된다.
;; 순열이 생성되는 규칙은 윗줄에서 동일 컬럼 전까지 모든 수를 합한것이 값이 된다.
;; 1
;; 1 2
;; 1 3 6
;; 1 4 10 20
;; 1 5 15 35 70
;; 1 6 21 56 126 252

(declare next-seq)
(deftest test-next-seq
  (is (= (next-seq [1]) [1 2]))
  (is (= (next-seq [1 2]) [1 3 6])))

;; 위 그림에서 next sequence를 만들어낸다.
(defn next-seq [s]
  (let [s1 (vec (map (fn [x] (reduce + (take x s)))
                     (range 1 (+ 1 (count s)))))]
    (conj s1 (* 2 (last s1)))))

(declare nth-seq-line)
(deftest test-nth-seq-line
  (is (= (nth-seq-line 1) [1]))
  (is (= (nth-seq-line 4) [1 4 10 20])))

;; n번째 sequece를 만든다.
(defn nth-seq-line [n]
  (loop [i 1
        s [1]]
    (if (= i n)
      s
      (recur (inc i) (next-seq s)))))

;; (* 2 (reduce + (nth-seq-line 20)))
;; => 137846528820