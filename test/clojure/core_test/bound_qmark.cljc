(ns clojure.core-test.bound-qmark
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

(def ^:dynamic *bound-var* 10)
(def ^:dynamic *unbound-var*)

(when-var-exists bound?
  (deftest test-bound-dynamic-vars
    (testing "returns true for bound dynamic var"
      (is (true? (bound? #'*bound-var*))))
    
    (testing "returns false for unbound dynamic var"
      (is (false? (bound? #'*unbound-var*))))
    
    (testing "returns true after binding unbound var"
      (binding [*unbound-var* 42]
        (is (true? (bound? #'*unbound-var*))))))
  
  (deftest test-regular-vars
    (testing "returns true for regular def"
      (def regular-var 100)
      (is (true? (bound? #'regular-var))))
    
    (testing "returns true for defn"
      (defn test-fn [] :result)
      (is (true? (bound? #'test-fn)))))
  
  (deftest test-nil-value
    (testing "bound to nil still returns true"
      (def ^:dynamic *nil-var* nil)
      (is (true? (bound? #'*nil-var*)))))
  
  (deftest test-binding-context
    (testing "bound within binding context"
      (is (true? (bound? #'*bound-var*)))
      (binding [*bound-var* 20]
        (is (true? (bound? #'*bound-var*)))))))
