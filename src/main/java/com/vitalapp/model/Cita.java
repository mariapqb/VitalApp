package com.vitalapp.model;

import java.time.LocalDateTime;

public class Cita {
    private String id;
    private String usuarioId;
    private LocalDateTime fechaHora;
    private String motivo;

    public Cita(String id, String usuarioId, LocalDateTime fechaHora, String motivo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }

    public void setId(String id) { this.id = id; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}

