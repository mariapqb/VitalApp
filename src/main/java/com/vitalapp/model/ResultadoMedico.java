package com.vitalapp.model;

public class ResultadoMedico {
    private String id;
    private String usuarioId;
    private String descripcion;
    private String fecha;

    public ResultadoMedico(String id, String usuarioId, String descripcion, String fecha) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getDescripcion() { return descripcion; }
    public String getFecha() { return fecha; }

    public void setId(String id) { this.id = id; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
