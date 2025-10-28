(ns clojure.core-test.boolean-array
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

#?(:clj
   (when-var-exists boolean-array
     (deftest test-create-array
       (testing "creates array with size"
         (let [arr (boolean-array 5)]
           (is (= (alength arr) 5))
           (is (= (class arr) (Class/forName "[Z")))))
       
       (testing "creates empty array"
         (let [arr (boolean-array 0)]
           (is (= (alength arr) 0)))))
     
     (deftest test-from-collection
       (testing "creates from collection of booleans"
         (let [arr (boolean-array [true false true])]
           (is (= (alength arr) 3))
           (is (= (aget arr 0) true))
           (is (= (aget arr 1) false))
           (is (= (aget arr 2) true))))
       
       (testing "creates from list"
         (let [arr (boolean-array '(false true))]
           (is (= (alength arr) 2))
           (is (= (aget arr 0) false))
           (is (= (aget arr 1) true)))))
     
     (deftest test-default-values
       (testing "initialized with false by default"
         (let [arr (boolean-array 3)]
           (is (= (aget arr 0) false))
           (is (= (aget arr 1) false))
           (is (= (aget arr 2) false)))))
     
     (deftest test-mutability
       (testing "array is mutable"
         (let [arr (boolean-array 3)]
           (aset arr 1 true)
           (is (= (aget arr 1) true))
           (is (= (aget arr 0) false))))))
   
   :cljs
   (when-var-exists boolean-array
     (deftest test-boolean-array
       (testing "boolean-array not in ClojureScript"
         (is true)))))
