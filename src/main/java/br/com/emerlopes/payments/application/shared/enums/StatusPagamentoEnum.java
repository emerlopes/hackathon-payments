package br.com.emerlopes.payments.application.shared.enums;

public enum StatusPagamentoEnum {
    PENDENTE("Pagamento Pendente"),
    PROCESSANDO("Pagamento em Processamento"),
    APROVADO("Pagamento Aprovado"),
    RECUSADO("Pagamento Recusado"),
    CANCELADO("Pagamento Cancelado");

    private final String descricao;

    StatusPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusPagamentoEnum fromDescricao(String descricao) {
        for (StatusPagamentoEnum statusPagamentoEnum : StatusPagamentoEnum.values()) {
            if (statusPagamentoEnum.getDescricao().equalsIgnoreCase(descricao)) {
                return statusPagamentoEnum;
            }
        }
        return null;
    }
}

