package br.com.emerlopes.payments.application.shared;

public class CartaoUtils {

    public static String mascararCartaoCredito(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.length() < 4) {
            throw new IllegalArgumentException("Número do cartão de crédito inválido.");
        }

        String ultimosQuatroDigitos = numeroCartao.substring(numeroCartao.length() - 4);
        StringBuilder mascarado = new StringBuilder();

        for (int i = 0; i < numeroCartao.length() - 4; i++) {
            mascarado.append('*');
        }

        mascarado.append(ultimosQuatroDigitos);
        return mascarado.toString();
    }
}