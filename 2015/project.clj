(defproject adventofcode "0.1.0-SNAPSHOT"
  :description "Clojure solutions for Advent of Code"
  :url "https://github.com/rupertl/adventofcode"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/math.combinatorics "0.1.1"]
                 [org.clojure/data.json "0.2.6"]]
  :jvm-opts ["-Xss4m"]
  :main ^:skip-aot adventofcode.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
