package com.vitalapp;

import com.vitalapp.model.*;
import com.vitalapp.monitoring.MetricsServer;
import com.vitalapp.service.VitalAppService;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@SuppressWarnings("checkstyle:MissingJavadocType")
public class Main {
    public static void main(String[] args) {
        // Iniciar servidor de métricas
        MetricsServer.start();

        // Crear el registro de métricas de Prometheus
        PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

        // Pasar el registro de métricas al servicio
        VitalAppService app = new VitalAppService(prometheusRegistry);
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("=== Bienvenido a VitalApp ===");

        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Agendar cita");
            System.out.println("3. Consultar resultados médicos");
            System.out.println("4. Enviar alerta personalizada");
            System.out.println("5. Ver alertas recibidas");
            System.out.println("6. Salir");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("ID de usuario: ");
                    String id = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();
                    app.registrarUsuario(id, nombre, correo);
                    System.out.println("✅ Usuario registrado.");
                    break;

                case "2":
                    System.out.print("ID de usuario: ");
                    String userId = scanner.nextLine();
                    System.out.print("Motivo de la cita: ");
                    String motivo = scanner.nextLine();

                    // Intentar leer la fecha y hora con el formato deseado
                    LocalDateTime fecha = null;
                    while (fecha == null) {
                        System.out.print("Fecha y hora (dd/MM/yyyy HH:mm): ");
                        String fechaStr = scanner.nextLine();
                        try {
                            fecha = LocalDateTime.parse(fechaStr, formatter); // Usar el formato definido
                        } catch (DateTimeParseException e) {
                            System.out.println("⚠ Formato de fecha incorrecto. Intenta de nuevo.");
                        }
                    }

                    app.agendarCita(UUID.randomUUID().toString(), userId, fecha, motivo);
                    System.out.println("📅 Cita agendada.");
                    break;

                case "3":
                    System.out.print("ID de usuario: ");
                    String uId = scanner.nextLine();
                    List<ResultadoMedico> resultados = app.obtenerResultadosUsuario(uId);
                    if (resultados.isEmpty()) {
                        System.out.println("⚠ No hay resultados.");
                    } else {
                        resultados.forEach(r -> System.out.println("- " + r.getDescripcion() + " [" + r.getFecha() + "]"));
                    }
                    break;

                case "4":
                    System.out.print("ID de usuario: ");
                    String usuarioAlerta = scanner.nextLine();
                    System.out.print("Mensaje de alerta: ");
                    String mensaje = scanner.nextLine();
                    app.enviarAlerta(mensaje, usuarioAlerta);
                    System.out.println("📢 Alerta enviada.");
                    break;

                case "5":
                    System.out.println("📨 Alertas:");
                    app.obtenerAlertas().forEach(a ->
                            System.out.println("- Para usuario " + a.getUsuarioId() + ": " + a.getMensaje()));
                    break;

                case "6":
                    System.out.println("Hasta luego.");
                    System.exit(0);
                    break;

                default:
                    System.out.println(" Opción inválida. Intente nuevamente.");
            }
        }
    }
}
