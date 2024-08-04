package br.com.emerlopes.payments.application.shared.enums;

public enum MetodoPagamentoEnum {
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    BOLETO("Boleto Bancário"),
    PAYPAL("PayPal"),
    PIX("Pix"),
    TRANSFERENCIA_BANCARIA("Transferência Bancária"),
    DINHEIRO("Dinheiro");

    private final String descricao;

    MetodoPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static MetodoPagamentoEnum fromDescricao(String descricao) {
        for (MetodoPagamentoEnum metodoPagamentoEnum : MetodoPagamentoEnum.values()) {
            if (metodoPagamentoEnum.getDescricao().equalsIgnoreCase(descricao)) {
                return metodoPagamentoEnum;
            }
        }
        return null;
    }

}
