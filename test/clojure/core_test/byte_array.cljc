(ns clojure.core-test.byte-array
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

#?(:clj
   (when-var-exists byte-array
     (deftest test-create-array
       (testing "creates array with size"
         (let [arr (byte-array 5)]
           (is (= (alength arr) 5))
           (is (= (class arr) (Class/forName "[B")))))
       
       (testing "creates empty array"
         (let [arr (byte-array 0)]
           (is (= (alength arr) 0)))))
     
     (deftest test-from-collection
       (testing "creates from collection of bytes"
         (let [arr (byte-array [1 2 3])]
           (is (= (alength arr) 3))
           (is (= (aget arr 0) 1))
           (is (= (aget arr 1) 2))
           (is (= (aget arr 2) 3))))
       
       (testing "creates from list"
         (let [arr (byte-array '(10 20 30))]
           (is (= (alength arr) 3))
           (is (= (aget arr 0) 10))
           (is (= (aget arr 1) 20))
           (is (= (aget arr 2) 30))))
       
       (testing "handles byte range"
         (let [arr (byte-array [127 -128 0])]
           (is (= (aget arr 0) 127))
           (is (= (aget arr 1) -128))
           (is (= (aget arr 2) 0)))))
     
     (deftest test-default-values
       (testing "initialized with 0 by default"
         (let [arr (byte-array 3)]
           (is (= (aget arr 0) 0))
           (is (= (aget arr 1) 0))
           (is (= (aget arr 2) 0)))))
     
     (deftest test-mutability
       (testing "array is mutable"
         (let [arr (byte-array 3)]
           (aset-byte arr 1 42)
           (is (= (aget arr 1) 42))
           (is (= (aget arr 0) 0)))))
     
     (deftest test-overflow
       (testing "handles overflow by wrapping"
         (let [arr (byte-array [128 256])]
           (is (= (aget arr 0) -128))
           (is (= (aget arr 1) 0))))))
   
   :cljs
   (when-var-exists byte-array
     (deftest test-byte-array
       (testing "byte-array not in ClojureScript"
         (is true)))))
