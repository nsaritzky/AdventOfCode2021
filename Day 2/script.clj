(ns script
  (:require [clojure.string :as str]))

(defn get-lines [file]
  (str/split-lines (slurp file)))

(defn parse-line [line]
  (let [[dir dist] (str/split line #" ")]
    [(keyword dir) (read-string dist)]))

(def move-map {:forward (fn [d [x y]] [(+ x d) y])
               :up (fn [d [x y]] [x (- y d)])
               :down (fn [d [x y]] [x (+ y d)])})

(defn make-move [pos [dir dist]]
  ((get move-map dir) dist pos))

(let [input (get-lines "/Users/nathan/Documents/projects/AdventOfCode2021/Day 2/input.txt")]
  (->> input
       (map parse-line)
       (reduce make-move [0 0])
       (apply *)));; => 1714950
;;    1714950;; => 1714950 Correct!

;; Part 2

(defn move [[[x y] aim] [dir dist]]
  (case dir
    :forward [[(+ x dist) (+ y (* aim dist))]
              aim]
    :up [[x y]
         (- aim dist)]
    :down [[x y]
           (+ aim dist)]))

(let [input (get-lines "/Users/nathan/Documents/projects/AdventOfCode2021/Day 2/input.txt")]
  (->>  input
       (map parse-line)
       (reduce move [[0 0] 0])))
;; => [[1850 692961] 927]
;;    [[1850 692961] 927];; => [[1850 692961] 927]

(apply * [1850 692961])
;; => 1281977850
;;    1281977850;; => 1281977850 Correct!
