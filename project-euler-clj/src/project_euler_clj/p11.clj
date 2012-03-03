(ns project-euler-clj.p11
  (:use clojure.test))

;; In the 20×20 grid below, four numbers along a diagonal line have been marked in red.

(def source "08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08
49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00
81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65
52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91
22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50
32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70
67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21
24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72
21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95
78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92
16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57
86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58
19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40
04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66
88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69
04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36
20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16
20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54
01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48")

;; The product of these numbers is 26 × 63 × 78 × 14 = 1788696.

;; What is the greatest product of four adjacent numbers in any direction
;; (up, down, left, right, or diagonally) in the 20×20 grid?

;; (def data )

(defn split-lines [s]
  (re-seq #"(?m).+$" (.trim s)))

(defn split-cols [l]
  (re-seq #"\w+" (.trim l)))

(defn matrix [d]
  (->> (split-lines d)
       (map split-cols)))

(deftest test-split
  (is (= (matrix "") []))
  (is (= (matrix " ") []))
  (is (= (matrix "a b c") [["a" "b" "c"]]))
  (is (= (matrix "a b c
d e f") [["a" "b" "c"] ["d" "e" "f"]])))

(defn to-int [m]
  (vec (map
        (fn [r] (vec (map #(Integer. %) r))) m)))

(deftest test-to-int
  (is (= (to-int [["01" "02"] ["03" "04"]]) [[1 2] [3 4]])))

(defn window [mat size row col]
  (map #(subvec % col (+ col size))
       (subvec mat row (+ row size))))

(deftest test-window
  (let [d [[ 1  2  3  4  5]
           [ 6  7  8  9 10]
           [11 12 13 14 15]
           [16 17 18 19 20]
           [21 22 23 24 25]]]
    (is (= (window d 2 0 0) [[1 2] [6 7]]))
    (is (= (window d 2 3 0) [[16 17] [21 22]]))))

(defn mat-row-products [m]
  (map #(reduce * 1 %) m))

(defn mat-col-products [m]
  (apply map (cons * m)))

(defn mat-diagonal-products [m]
  (let [size (count m)
        rng (range size)
        d1 (map #(nth (nth m %) %) rng)
        d2 (map #(nth (nth m %) (- size % 1)) rng)]
    [(reduce * 1 d1) (reduce * 1 d2)]))

(defn max-mat-products [mat]
  (apply max
         (reduce conj 
                 (reduce conj
                         (mat-row-products mat)
                         (mat-col-products mat))
                 (mat-diagonal-products mat))))

(deftest test-mat-products
  (let [mat [[1 2 3]
             [4 5 6]
             [7 8 9]]]
    (is (= (mat-row-products mat) [6 120 504]))
    (is (= (mat-col-products mat) [28 80 162]))
    (is (= (mat-diagonal-products mat) [45 105]))
    (is (= (max-mat-products mat) 504))))

(defn p11 []
  (let [window-size 4
        data (to-int (matrix source))
        rng (range (- (count data) window-size))]
    (apply max (for [r rng c rng]
                 (max-mat-products
                  (window data window-size r c))))))

(deftest test-p11
  (is (= (p11) 70600674)))

;; mat-diagonal-products에서 nth가 필요하다.
;; vector 이어야 하는데 이걸 to-int에서 하고 있음.
;; 좋지 않음.
