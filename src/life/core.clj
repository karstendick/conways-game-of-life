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
