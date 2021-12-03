(ns day1
  (:require [babashka.curl :as curl]
            [clojure.string :as str]))

(defn get-lines [file]
  (str/split-lines (slurp file)))

(let [input (map read-string
                    (get-lines "/Users/nathan/Documents/projects/AdventOfCode2021/Day 1/input.txt"))
        changes (map-indexed #(- %2 (nth (cons 0 input) %)) input)]
     (count
      (->> changes
           (filter #(> % 0))
           next)))      ; -> 1162

(let [input (map read-string
                    (get-lines "/Users/nathan/Documents/projects/AdventOfCode2021/Day 1/input.txt"))
        changes (map-indexed #(- %2 (nth (cons 0 input) %)) input)]
  (->> input
       (#(map + % (cons 0 %) (concat [0 0] %)))
       nnext
       (#(map - % (cons 0 %)))
       next
       (filter #(> % 0))
       count))                          ; -> 1190
