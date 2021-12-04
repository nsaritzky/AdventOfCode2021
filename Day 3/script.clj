(ns script
  (:require [clojure.string :as str]))

(defn get-lines [file]
  (str/split-lines (slurp file)))

(def input (get-lines "/Users/nathan/Documents/projects/AdventOfCode2021/Day 3/input.txt"))

(def gamma-str (->> input
       (map #(str/split % #""))
       (map (partial mapv read-string))
       (reduce (partial mapv +))
       (map (let [len (/ (count input) 2)]
              #(cond
                 (< % len) 0
                 (> % len) 1)))
       (str/join)))

(def gamma (read-string (str "2r" gamma-str)))

(def epsilon-str (str/join
                  (map #(case % "0" "1" "1" "0")
                       (str/split gamma-str #""))))

(def epsilon (read-string (str "2r" epsilon-str)))

(* epsilon gamma)
;; => 749376
;;    749376;; => 749376 Correct!

;; Part 2

(defn filter-by-nth-bit [coll n majority?]
  (let [majority (->> coll
                      (map (comp read-string str #(nth % n)))
                      (reduce +)
                      (#(let [threshold (/ (count coll) 2)]
                          (cond
                            (>= % threshold) (if majority? 1 0)
                            (< % threshold) (if majority? 0 1)
                            ))))]
    (filter #(= (str (nth % n)) (str majority)) coll)))

(defn filter-to-one
  ([coll majority?]
   (filter-to-one coll 0 majority?))
  ([coll i majority?]
   (let [n (count (first input))]
     (if (= (count coll) 1)
       (first coll)
       (filter-to-one (filter-by-nth-bit coll i majority?) (inc i) majority?)))))

(read-string (str "2r" (filter-to-one input true)));; => 3871
(read-string (str "2r" (filter-to-one input false)));; => 613

(* 3871 613);; => 2372923 Correct!
;; => 4194303
