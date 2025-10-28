(ns clojure.core-test.bounded-count
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

(when-var-exists bounded-count
  (deftest test-counted-collections
    (testing "returns actual count for counted collections"
      (is (= (bounded-count 10 [1 2 3]) 3))
      (is (= (bounded-count 2 [1 2 3 4 5]) 5))
      (is (= (bounded-count 5 [1 2]) 2)))
    
    (testing "returns 0 for empty collection"
      (is (= (bounded-count 10 []) 0))
      (is (= (bounded-count 1 []) 0))))
  
  (deftest test-lazy-sequences
    (testing "stops at limit for lazy sequences exceeding limit"
      (is (= (bounded-count 2 (filter odd? (range 100))) 2))
      (is (= (bounded-count 5 (map inc (range 100))) 5)))
    
    (testing "returns actual for lazy sequences below limit"
      (is (= (bounded-count 10 (take 3 (range))) 3))))
  
  (deftest test-infinite-sequences
    (testing "stops at limit for infinite sequences"
      (is (= (bounded-count 10 (repeat 1)) 10))
      (is (= (bounded-count 5 (cycle [1 2 3])) 5))
      (is (= (bounded-count 100 (iterate inc 0)) 100))))
  
  (deftest test-nil-handling
    (testing "returns 0 for nil"
      (is (= (bounded-count 10 nil) 0))))
  
  (deftest test-different-counted-types
    (testing "works with vectors"
      (is (= (bounded-count 10 [1 2 3 4]) 4)))
    
    (testing "works with lists"
      (is (= (bounded-count 10 '(1 2 3)) 3)))
    
    (testing "works with sets"
      (is (= (bounded-count 10 #{1 2 3}) 3)))
    
    (testing "works with maps"
      (is (= (bounded-count 10 {:a 1 :b 2}) 2)))
    
    (testing "works with strings"
      (is (= (bounded-count 10 "hello") 5))))
  
  (deftest test-zero-limit
    (testing "returns actual count for counted with zero limit"
      (is (= (bounded-count 0 [1 2 3]) 3)))
    
    (testing "returns 0 for lazy with zero limit"
      (is (= (bounded-count 0 (filter odd? (range 10))) 0)))))
