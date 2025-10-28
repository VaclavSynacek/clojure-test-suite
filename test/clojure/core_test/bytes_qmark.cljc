(ns clojure.core-test.bytes-qmark
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

#?(:clj
   (when-var-exists bytes?
     (deftest test-byte-arrays
       (testing "returns true for byte array"
         (is (true? (bytes? (byte-array 5))))
         (is (true? (bytes? (byte-array [1 2 3])))))
       
       (testing "returns true for empty byte array"
         (is (true? (bytes? (byte-array 0))))))
     
     (deftest test-other-arrays
       (testing "returns false for int array"
         (is (false? (bytes? (int-array 5)))))
       
       (testing "returns false for boolean array"
         (is (false? (bytes? (boolean-array 5)))))
       
       (testing "returns false for char array"
         (is (false? (bytes? (char-array 5)))))
       
       (testing "returns false for long array"
         (is (false? (bytes? (long-array 5))))))
     
     (deftest test-non-arrays
       (testing "returns false for vector"
         (is (false? (bytes? [1 2 3]))))
       
       (testing "returns false for list"
         (is (false? (bytes? '(1 2 3)))))
       
       (testing "returns false for string"
         (is (false? (bytes? "hello"))))
       
       (testing "returns false for number"
         (is (false? (bytes? 42))))
       
       (testing "returns false for nil"
         (is (false? (bytes? nil))))))
   
   :cljs
   (when-var-exists bytes?
     (deftest test-bytes?
       (testing "bytes? not in ClojureScript"
         (is true)))))
