(ns life.core-test
  (:require [midje.sweet :refer :all]
            [life.core :refer :all]))

(tabular
  (fact (step-cell ?cell ?n) => ?state)
  ?cell ?n     ?state
  life  0      dead
  life  1      dead
  life  2      life
  life  3      life
  life  4      dead
  life  5      dead
  life  6      dead
  life  7      dead
  life  8      dead
  
  dead  0      dead
  dead  1      dead
  dead  2      dead
  dead  3      life
  dead  4      dead
  dead  5      dead
  dead  6      dead
  dead  7      dead
  dead  8      dead)
