package com.musicale.aldrighi.escala.dto;

/**
 * Aviso de que um feriado cai em um dia comercial da escola.
 *
 * @param data        data do feriado no formato dd/MM/yyyy
 * @param nome        nome do feriado
 * @param diaComercial descrição do dia comercial afetado (ex.: "Sexta-feira")
 * @param totalAulas  quantidade de aulas escaladas nesse dia da semana
 */
public record AlertaFeriadoDTO(String data, String nome, String diaComercial, int totalAulas) {
}
