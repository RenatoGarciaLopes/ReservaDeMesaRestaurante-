package com.example.demo.enums;

import java.text.Normalizer;
import java.time.DayOfWeek;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DiaSemanaPortugues {
    SEGUNDA,
    TERCA,
    QUARTA,
    QUINTA,
    SEXTA,
    SABADO,
    DOMINGO;

    @JsonCreator
    public static DiaSemanaPortugues from(String dia) {
        String normalizado = Normalizer.normalize(dia.toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return switch (normalizado) {
            case "segunda-feira" -> SEGUNDA;
            case "terca-feira" -> TERCA;
            case "quarta-feira" -> QUARTA;
            case "quinta-feira" -> QUINTA;
            case "sexta-feira" -> SEXTA;
            case "sabado" -> SABADO;
            case "domingo" -> DOMINGO;
            default -> throw new IllegalArgumentException("Dia inválido: " + dia);
        };
    }

    public DayOfWeek toDayOfWeek() {
        return switch (this) {
            case SEGUNDA -> DayOfWeek.MONDAY;
            case TERCA -> DayOfWeek.TUESDAY;
            case QUARTA -> DayOfWeek.WEDNESDAY;
            case QUINTA -> DayOfWeek.THURSDAY;
            case SEXTA -> DayOfWeek.FRIDAY;
            case SABADO -> DayOfWeek.SATURDAY;
            case DOMINGO -> DayOfWeek.SUNDAY;
        };
    }
}
