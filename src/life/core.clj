(ns life.core)

(def life ".")
(def dead " ")

(defn live? [x] (= life x))
(defn dead? [x] (= dead x))

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
  (and (in-bounds? r c num-rows num-cols)
       (live? (cell-at board r c))))

(defn count-neighbors
  ([board r c num-rows num-cols]
   (count-neighbors board r c num-rows num-cols cell-at-live?))
  ([board r c num-rows num-cols pred?]
   (count
    (filter true?
            [(pred? board r (dec c) num-rows num-cols)
             (pred? board r (inc c) num-rows num-cols)
             (pred? board (dec r) c num-rows num-cols)
             (pred? board (inc r) c num-rows num-cols)
             (pred? board (inc r) (inc c) num-rows num-cols)
             (pred? board (dec r) (inc c) num-rows num-cols)
             (pred? board (inc r) (dec c) num-rows num-cols)
             (pred? board (dec r) (dec c) num-rows num-cols)]))))
