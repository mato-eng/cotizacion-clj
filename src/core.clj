(ns core
  (:require [clojure.data.json :as json]))

(def uri-dolar-ambito "https://mercados.ambito.com/dolar/informal/variacion")

(defn main [opts]
  (let [response (slurp uri-dolar-ambito)
        values (json/read-str response :key-fn keyword)]
    (println "COTIZACION")
    (printf "Hoy %s compra: %s - venta: %s" (:fecha values) (:compra values) (:venta values))))

