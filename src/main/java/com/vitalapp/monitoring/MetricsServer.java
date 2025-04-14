package com.vitalapp.monitoring;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import static spark.Spark.*;

@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:LineLength"})
public class MetricsServer {

    @SuppressWarnings("checkstyle:Indentation")
    private static final PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public static void start() {
        port(8085); // cambia si lo necesitas

        System.out.println("🚀 Iniciando servidor Spark en http://localhost:8085/metrics");


        get("/metrics", (req, res) -> {
            res.type("text/plain");
            return prometheusRegistry.scrape();
        });
        awaitInitialization();
        System.out.println("✅ Servidor listo.");
    }

    @SuppressWarnings("checkstyle:Indentation")
    public static PrometheusMeterRegistry getRegistry() {
        return prometheusRegistry;
    }
}

