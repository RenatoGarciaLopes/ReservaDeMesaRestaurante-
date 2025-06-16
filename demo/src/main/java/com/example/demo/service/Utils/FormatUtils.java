package com.example.demo.service.Utils;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class FormatUtils {

    public static String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11)
            return cpf;
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formatarTelefone(String telefone) {
        if (telefone == null || (telefone.length() != 10 && telefone.length() != 11))
            return telefone;

        if (telefone.length() == 11) {
            return telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1)$2-$3");
        } else {
            return telefone.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1)$2-$3");
        }
    }

    public static String formatarDiaFuncionamento(DayOfWeek dia) {
        String diaFromatado = dia.getDisplayName(TextStyle.FULL, new Locale("Pt", "BR"));
        return diaFromatado.substring(0, 1).toUpperCase() + diaFromatado.substring(1);
    }
}
