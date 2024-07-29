package br.com.emerlopes.payments.application.shared;

public class CpfUtils {
    public static String mascararCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos.");
        }

        return "***.***." + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }
}
