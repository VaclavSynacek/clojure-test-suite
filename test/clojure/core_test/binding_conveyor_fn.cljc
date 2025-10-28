(ns clojure.core-test.binding-conveyor-fn
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

#?(:bb
   (deftest test-binding-conveyor-fn-stub
     (testing "binding-conveyor-fn not in Babashka"
       (is true)))
   
   :clj
   (do
     (def ^:dynamic *test-var* :original)
     
     (when-var-exists binding-conveyor-fn
       (deftest test-basic-functionality
         (testing "returns a function"
           (is (fn? (binding-conveyor-fn (fn [])))))
         
         (testing "returned function is callable"
           (let [f (binding-conveyor-fn (fn [] :result))]
             (is (= (f) :result)))))
       
       (deftest test-binding-conveyance
         (testing "conveys bindings to called function"
           (let [f (binding [*test-var* :bound]
                     (binding-conveyor-fn (fn [] *test-var*)))]
             (is (= (f) :bound)))))
       
       (deftest test-with-arguments
         (testing "passes arguments through"
           (let [f (binding-conveyor-fn (fn [x y] (+ x y)))]
             (is (= (f 1 2) 3))))
         
         (testing "passes multiple arguments"
           (let [f (binding-conveyor-fn (fn [& args] args))]
             (is (= (f 1 2 3 4) '(1 2 3 4))))))
       
       (deftest test-nil-handling
         (testing "works with nil-returning function"
           (let [f (binding-conveyor-fn (fn [] nil))]
             (is (nil? (f))))))))
   
   :cljs
   (when-var-exists binding-conveyor-fn
     (deftest test-binding-conveyor-fn
       (testing "binding-conveyor-fn not in ClojureScript"
         (is true)))))
