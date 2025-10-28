(ns clojure.core-test.bases
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

#?(:clj
   (when-var-exists bases
     #?@(:bb
         [(deftest test-bases
            (testing "bases not available in Babashka"
              (is true)))]
         :default
         [(do
            (definterface TestInterface)
            (deftype TestType []
              TestInterface)
            
            (definterface TestInterface2)
            (deftype MultiType []
              TestInterface
              TestInterface2)
            
            (defrecord TestRecord [x y]))
          
          (deftest test-basic-usage
            (testing "returns seq of base classes/interfaces"
              (let [b (bases TestType)]
                (is (seq? b))
                (is (some #(= % TestInterface) b))))
            
            (testing "returns nil for Object"
              (is (nil? (bases Object))))
            
            (testing "returns bases for standard classes"
              (let [b (bases Integer)]
                (is (seq b))
                (is (some #(= % Number) b))))
            
            (testing "returns nil for nil"
              (is (nil? (bases nil)))))
          
          (deftest test-with-records
            (testing "records have bases"
              (let [b (bases TestRecord)]
                (is (seq b))
                (is (some #(= % clojure.lang.IRecord) b)))))
          
          (deftest test-multiple-interfaces
            (testing "includes all declared interfaces"
              (let [b (set (bases MultiType))]
                (is (contains? b TestInterface))
                (is (contains? b TestInterface2))
                (is (>= (count b) 2)))))])))

#?(:cljs
   (when-var-exists bases
     (deftest test-bases
       (testing "bases not in ClojureScript"
         (is true)))))
