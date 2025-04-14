package com.vitalapp.model;

public class AlertaSalud {
    private String mensaje;
    private String usuarioId;

    public AlertaSalud(String mensaje, String usuarioId) {
        this.mensaje = mensaje;
        this.usuarioId = usuarioId;
    }

    public String getMensaje() { return mensaje; }
    public String getUsuarioId() { return usuarioId; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}
