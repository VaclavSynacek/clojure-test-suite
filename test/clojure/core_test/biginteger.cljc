(ns clojure.core-test.biginteger
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

#?(:clj
   (when-var-exists biginteger
     (deftest test-basic-conversion
       (testing "converts integer to BigInteger"
         (is (instance? java.math.BigInteger (biginteger 42)))
         (is (= (biginteger 42) (java.math.BigInteger/valueOf 42))))
       
       (testing "converts long to BigInteger"
         (is (instance? java.math.BigInteger (biginteger 9223372036854775807)))
         (is (= (biginteger 9223372036854775807) (java.math.BigInteger/valueOf 9223372036854775807))))
       
       (testing "converts string to BigInteger"
         (is (instance? java.math.BigInteger (biginteger "12345678901234567890")))
         (is (= (str (biginteger "12345678901234567890")) "12345678901234567890")))
       
       (testing "converts BigInt to BigInteger"
         (is (instance? java.math.BigInteger (biginteger 12345678901234567890N)))
         (is (= (biginteger 12345678901234567890N) (java.math.BigInteger. "12345678901234567890")))))
     
     (deftest test-zero-and-one
       (testing "zero"
         (is (= (biginteger 0) java.math.BigInteger/ZERO)))
       
       (testing "one"
         (is (= (biginteger 1) java.math.BigInteger/ONE)))
       
       (testing "negative one"
         (is (= (biginteger -1) (java.math.BigInteger/valueOf -1)))))
     
     (deftest test-negative-numbers
       (testing "negative integer"
         (is (= (biginteger -42) (java.math.BigInteger/valueOf -42))))
       
       (testing "negative large number"
         (is (= (str (biginteger "-99999999999999999999")) "-99999999999999999999"))))
     
     (deftest test-large-numbers
       (testing "very large positive number"
         (let [big "123456789012345678901234567890"]
           (is (= (str (biginteger big)) big))))
       
       (testing "very large negative number"
         (let [big "-123456789012345678901234567890"]
           (is (= (str (biginteger big)) big)))))
     
     (deftest test-preserves-value
       (testing "roundtrip through biginteger"
         (is (= 42 (int (biginteger 42))))
         (is (= 100 (long (biginteger 100)))))))
   
   :cljs
   (when-var-exists biginteger
     (deftest test-biginteger
       (testing "biginteger not in ClojureScript"
         (is true)))))
