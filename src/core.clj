(ns core
  (:gen-class)
  (:require [clojure.data.json :as json]
            [dotenv :refer [env]]))

(def dolar-informal-link (env "DOLAR_INFORMAL"))
(def dolar-turista-link (env "DOLAR_TURISTA"))
(def cad-usd-link (env "DOLAR_CAD"))

(defn async-fetch-info [url]
  (future (slurp url)))

(defn async-parse-json [data]
  (future (json/read-json @data)))


(defn -main [& args]
  (let [dolar-informal (async-fetch-info dolar-informal-link)
        dolar-turista (async-fetch-info dolar-turista-link)
        cad-usd (async-fetch-info cad-usd-link)
        informal (async-parse-json dolar-informal)
        turista-value (async-parse-json dolar-turista)
        cad-usd-value (async-parse-json cad-usd)]
    (printf "DOLAR INFORMAL %s compra: %s - venta: %s\n" (:fecha @informal) (:compra @informal) (:venta @informal))
    (printf "DOLAR TURISTA %s compra: %s - venta: %s\n" (:fecha @turista-value) (:compra @turista-value) (:venta @turista-value))
    (printf "DOLAR-CAD %s" (get-in (first (:observations @cad-usd-value)) [:FXUSDCAD :v]))
    (println)))