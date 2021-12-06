(ns script
  (:require [clojure.string :as str]
            [clojure.walk :as w]))

(defn get-lines [file]
  (str/split-lines (slurp file)))

(def raw-input (get-lines "./input.txt"))

(def bingo-draws (let [raw (str/split (first raw-input) #",")]
                   (map read-string raw)))

(def bingo-cards
  (letfn [(parse-card [strings]
            (->> strings
                 (mapv #(str/split (str/trim %) #" +"))
                 (mapv #(mapv read-string %))))]
    (->> (nnext raw-input)
         (remove #(= "" %))
         (partition 5)
         (map parse-card))))

(defn evaluate-bingo-draw [n cards]
  (w/prewalk-replace {n nil} cards))

(defun check-for-bingo
  (letfn [(checked? [entry] (= nil entry))
          (check-rows [card]
            (w/walk #(every? checked? %) #(some true? %) card))
          (check-columns [card]
            (check-rows (transpose card)))
          (check-diagonals [card]
            (or (every? nil? (map-indexed #(nth %2 %) card))
                (every? nil? (map-indexed #(nth %2 (- 5 % 1)) card))))]
    (map (some-fn check-rows check-columns check-diagonals) bingo-cards)))

(defn transpose [m]
  (apply mapv vector m))

(defn calculate-score [card]
  ())
