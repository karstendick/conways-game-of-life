(ns life.core)

(def life ".")
(def dead " ")

(defn live? [x] (#{life 1} x))
(defn dead? [x] (#{dead 0} x))

(defn step-cell
  "Advances the state of cell with n neighbors."
  [cell n]
  (cond (and (dead? cell)
             (= 3 n))
        life
        
        (and (live? cell)
             (or (= 2 n)
                 (= 3 n)))
        life
        
        :else
        dead))

(defn cell-at
  "Takes a vector of vectors and returns the value at
  the given row and column"
  [vv r c]
  (get-in vv [r c]))

(defn in-bounds?
  [r c num-rows num-cols]
  (and (<= 0 r (dec num-rows))
       (<= 0 c (dec num-cols))))

(defn cell-at-live?
  [board r c num-rows num-cols]
  ; (when (in-bounds? r c num-rows num-cols)
  ;   (printf "in bounds: [%s %s]\n" r c))
  ; (when (live? (cell-at board r c))
  ;   (printf "live: [%s %s] %s\n" r c (cell-at board r c)))
  (and (in-bounds? r c num-rows num-cols)
       (live? (cell-at board r c))))

(defn count-neighbors
  ([board r c num-rows num-cols]
   (count-neighbors board r c num-rows num-cols cell-at-live?))
  ([board r c num-rows num-cols pred?]
   (count
    (filter identity
            [(pred? board r (dec c) num-rows num-cols)
             (pred? board r (inc c) num-rows num-cols)
             (pred? board (dec r) c num-rows num-cols)
             (pred? board (inc r) c num-rows num-cols)
             (pred? board (inc r) (inc c) num-rows num-cols)
             (pred? board (dec r) (inc c) num-rows num-cols)
             (pred? board (inc r) (dec c) num-rows num-cols)
             (pred? board (dec r) (dec c) num-rows num-cols)]))))

(def num-rows 20)
(def num-cols 20)

(defn empty-board
  [num-rows num-cols]
  (let [row (into [] (repeat num-cols dead))]
    (into [] (repeat num-rows row))))

(defn print-board
  [board]
  (doseq [row board]
    (println row)))

(defn step-cell*
  [board num-rows num-cols r c]
  (let [n (count-neighbors board r c num-rows num-cols)
        cell (cell-at board r c)]
    ; (printf "[%s %s] (%s) %s \n"
    ;         r c n cell)
    (step-cell cell n)))

(defn step-board
  [board num-rows num-cols]
  (into []
        (for [r (range num-rows)]
          (into []
                (for [c (range num-cols)]
                  (step-cell* board num-rows num-cols r c))))))

(def blinker
  [[0 0 0 0 0]
   [0 0 0 0 0]
   [0 1 1 1 0]
   [0 0 0 0 0]
   [0 0 0 0 0]])

(def glider
  [[0 0 1 0 0]
   [0 0 0 1 0]
   [0 1 1 1 0]
   [0 0 0 0 0]
   [0 0 0 0 0]])

(defn print-gens
  [board num-gens]
  (let [num-rows (count board)
        num-cols (count (first board))
        new-board (step-board board num-rows num-cols)]
    (loop [board board
           i 0]
      (print-board board)
      (println "\n")
      (when (< i num-gens)
        (recur (step-board board num-rows num-cols) (inc i))))))
