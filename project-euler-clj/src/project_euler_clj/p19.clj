(ns project-euler-clj.p19)

;; You are given the following information, but you may prefer to do some research for yourself.
;;     1 Jan 1900 was a Monday.
;;     Thirty days has September,
;;     April, June and November.
;;     All the rest have thirty-one,
;;     Saving February alone,
;;     Which has twenty-eight, rain or shine.
;;     And on leap years, twenty-nine.
;;     A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
;; How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?
;; 1901년 1월 1일부터 2000년 12월 31일 사이에 매월 1일이 일요일인 날은
;; 몇 일인가?

;; how to calculate day of the week?
;; http://en.wikipedia.org/wiki/Determination_of_the_day_of_the_week

(defn leap-year? [y]
  (or (= 0 (mod y 400))
      (and (= 0 (mod y 4))
           (not (= 0 (mod y 100))))))

(defn day-table [d]
  (mod d 7))

(defn month-table [y m]
  (let [days [31 28 31 30 31 30 31 31 30 31 30 31]
        is-leap (leap-year? y)
        table (map #(mod % 7)
                   (reduce #(conj %1 (+ (last %1) %2)) [0] days))]
    (nth
     (if is-leap
       (map + table (concat [-1 -1] (repeat 0)))
       table) (- m 1))))

(defn year-table [y]
  (let [yt (mod y 100)]
    (mod (+ yt (Math/round (- (/ yt 4.0) 0.5))) 7)))

(defn gregorian-century-table [y]
  (let [c (Math/round (- (/ y 100.0) 0.5))]
    (* (- 3 (mod c 4)) 2)))

(defn day-of-week [y m d]
  (let [dt (day-table d)
        mt (month-table y m)
        yt (year-table y)
        ct (gregorian-century-table y)]
    (mod (+ dt mt yt  ct) 7)))

(defn p19 [start end]
  (count (filter #(= % 0)
                 (map #(apply day-of-week %)
                      (for [y (range start (+ end 1))
                            m (range 1 13)]
                        [y m 1])))))
