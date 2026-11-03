package com.musicale.aldrighi.escala.dto;

/**
 * Feriado devolvido pela BrasilAPI (https://brasilapi.com.br/docs#tag/Feriados-Nacionais).
 * Exemplo: {"date":"2026-11-20","name":"Dia da Consciência Negra","type":"national"}
 */
public record FeriadoDTO(String date, String name, String type) {
}
