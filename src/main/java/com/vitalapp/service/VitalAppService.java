package com.vitalapp.service;

import com.vitalapp.model.*;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.time.LocalDateTime;
import java.util.*;

public class VitalAppService {
    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final Map<String, Cita> citas = new HashMap<>();
    private final Map<String, ResultadoMedico> resultados = new HashMap<>();
    private final List<AlertaSalud> alertas = new ArrayList<>();

    // MeterRegistry para Prometheus
    private final PrometheusMeterRegistry prometheusRegistry;

    // Contadores para las métricas
    private final Counter usuariosRegistrados;
    private final Counter citasAgendadas;
    private final Counter alertasEnviadas;
    private final Counter resultadosAgregados;

    public VitalAppService(PrometheusMeterRegistry prometheusRegistry) {
        this.prometheusRegistry = prometheusRegistry;
        // Inicializar los contadores
        this.usuariosRegistrados = prometheusRegistry.counter("usuarios_registrados_total");
        this.citasAgendadas = prometheusRegistry.counter("citas_agendadas_total");
        this.alertasEnviadas = prometheusRegistry.counter("alertas_enviadas_total");
        this.resultadosAgregados = prometheusRegistry.counter("resultados_agregados_total");
    }

    public Usuario registrarUsuario(String id, String nombre, String correo) {
        Usuario u = new Usuario(id, nombre, correo);
        usuarios.put(id, u);
        usuariosRegistrados.increment();  // Incrementa el contador cada vez que se registra un usuario
        return u;
    }

    public Cita agendarCita(String id, String usuarioId, LocalDateTime fecha, String motivo) {
        Cita cita = new Cita(id, usuarioId, fecha, motivo);
        citas.put(id, cita);
        citasAgendadas.increment();  // Incrementa el contador cada vez que se agenda una cita
        return cita;
    }

    public ResultadoMedico agregarResultado(String id, String usuarioId, String descripcion, String fecha) {
        ResultadoMedico r = new ResultadoMedico(id, usuarioId, descripcion, fecha);
        resultados.put(id, r);
        resultadosAgregados.increment();  // Incrementa el contador cada vez que se agrega un resultado
        return r;
    }

    public void enviarAlerta(String mensaje, String usuarioId) {
        alertas.add(new AlertaSalud(mensaje, usuarioId));
        alertasEnviadas.increment();  // Incrementa el contador cada vez que se envía una alerta
    }

    public List<Cita> obtenerCitasUsuario(String usuarioId) {
        List<Cita> lista = new ArrayList<>();
        for (Cita c : citas.values()) {
            if (c.getUsuarioId().equals(usuarioId)) {
                lista.add(c);
            }
        }
        return lista;
    }

    public List<ResultadoMedico> obtenerResultadosUsuario(String usuarioId) {
        List<ResultadoMedico> lista = new ArrayList<>();
        for (ResultadoMedico r : resultados.values()) {
            if (r.getUsuarioId().equals(usuarioId)) {
                lista.add(r);
            }
        }
        return lista;
    }

    public List<AlertaSalud> obtenerAlertas() {
        return alertas;
    }
}
