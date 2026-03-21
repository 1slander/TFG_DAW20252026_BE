package com.tfgbe.modelo.dto;

public class ChatResponseDto {
    private String respuesta;
    public ChatResponseDto(String respuesta) { this.respuesta = respuesta; }
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
}
