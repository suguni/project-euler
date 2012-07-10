(ns project-euler-clj.p17
  (:use clojure.test))

;; If the numbers 1 to 5 are written out in words:
;; one, two, three, four, five, then there are
;; 3 + 3 + 5 + 4 + 4 = 19 letters used in total.

;; If all the numbers from 1 to 1000 (one thousand)
;; inclusive were written out in words, how many letters would be used?

;; NOTE: Do not count spaces or hyphens.
;; For example, 342 (three hundred and forty-two) contains 23 letters
;; and 115 (one hundred and fifteen) contains 20 letters.
;; The use of "and" when writing out numbers is in compliance with
;; British usage.

(def words-1 ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine""ten"
              "eleven" "twelve" "thirteen" "fourteen" "fifteen" "sixteen" "seventeen" "eighteen" "nineteen"])
(def words-2 ["twenty" "thirty" "forty" "fifty" "sixty" "seventy" "eighty" "ninety"])
(def words-3 ["hundred" "thousand"])

(defn n-digit-number [n d]
  (Integer/parseInt (str (nth (reverse (str n)) (- d 1)))))

;; ??? 구리다.
(defn number-to-words [n]
  (cond (= n 0) []
        (and (>= n 1) (< n 20)) [(words-1 (- n 1))]
        (and (>= n 20) (< n 100))
        (let [d1 (n-digit-number n 1)
              d2 (n-digit-number n 2)
              r [(nth words-2 (- d2 2) :x)]]
          (if (= d1 0)
            r
            (conj r (nth words-1 (- d1 1) :x))))
        (and (>= n 100) (< n 1000))
        (let [d1 (n-digit-number n 1)
              d2 (n-digit-number n 2)
              d3 (n-digit-number n 3)
              r [(words-1 (- d3 1)) (words-3 0)]
              n (number-to-words (- n (* d3 100)))]
          (if (not (empty? n))
            (apply conj (conj r "and") n)
            r))
        :else ["one" (words-3 1)]))

(defn vector-string-count [v]
  (reduce #(+ %1 (count %2)) 0 v))

(defn p17 []
  (apply + (map vector-string-count (map number-to-words (range 1 1001)))))

(deftest test-vector-string-count
  (is (= (vector-string-count ["three" "hundred" "and" "forty" "two"]) 23))
  (is (= (vector-string-count ["one" "hundred" "and" "fifteen"]) 20)))

(deftest test-n-digit-number
  (is (= (n-digit-number 1234 2) 3)))

(deftest test-number-to-words
  (is (= (number-to-words 1) ["one"]))
  (is (= (number-to-words 10) ["ten"]))
  (is (= (number-to-words 13) ["thirteen"]))
  (is (= (number-to-words 23) ["twenty" "three"]))
  (is (= (number-to-words 90) ["ninety"]))
  (is (= (number-to-words 100) ["one" "hundred"]))
  (is (= (number-to-words 123) ["one" "hundred" "and" "twenty" "three"]))
  (is (= (number-to-words 342) ["three" "hundred" "and" "forty" "two"]))
  (is (= (number-to-words 115) ["one" "hundred" "and" "fifteen"]))
  (is (= (number-to-words 1000) ["one" "thousand"])))
