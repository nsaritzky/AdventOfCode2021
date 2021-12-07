(ns script
  (:require [clojure.string :as str]
            [clojure.walk :as w]
            [clojure.math.numeric-tower :as math]))

(defn get-lines [file]
  (str/split-lines (slurp file)))

(defn transpose [m]
  (apply mapv vector m))

(def raw-input (get-lines "./Day 5/input.txt"))

(defn parse-line [str]
  (->> (str/split str #" -> |,")
       (map read-string)
       (partition 2)
       (map vec)
       vec))

(parse-line (first raw-input))


(defn empty-board [n] (vec (repeat n (vec (repeat n 0)))))

(defn update-vals [m ks f]
  (reduce #(update-in % %2 f) m ks))

(defn mark-line [board [[x1 y1] [x2 y2]]]
  (letfn [(segment [x y] (range (min x y) (inc (max x y))))]
    (if (= x1 x2)
      (update-vals board (for [y (segment y1 y2)] [x1 y]) inc)
      (update-vals board (for [x (segment x1 x2)] [x y1]) inc))))

(mark-line (empty-board 10) [[2 8] [2 4]])

#_
(reduce + (for [[[x1 y1] [x2 y2]] (map parse-line raw-input)]
            (if ( = x1 x2)
              ())))




(defn count-intersections [n segments]
  (->> segments
       (reduce #(mark-line % %2) (empty-board n))
       flatten
       (filter #(<= 2 %))
       count))

(count-intersections 10 [[[2 6] [2 2]]
                         [[1 4] [8 4]]
                         [[2 4] [6 4]]])

(count-intersections 1000 (map parse-line raw-input))
