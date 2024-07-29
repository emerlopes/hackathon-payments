package br.com.emerlopes.payments.application.entrypoint.rest.pagamento;

import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.RegistrarPagamentoRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.RegistrarPagamentoResponseDTO;
import br.com.emerlopes.payments.application.shared.CustomResponseDTO;
import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.usecase.pagamento.RegistrarPagamentoCartaoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final RegistrarPagamentoCartaoUseCase registrarPagamentoCartaoUseCase;

    public PagamentoController(
            final RegistrarPagamentoCartaoUseCase registrarPagamentoCartaoUseCase
    ) {
        this.registrarPagamentoCartaoUseCase = registrarPagamentoCartaoUseCase;
    }

    @PostMapping
    @PreAuthorize("@securityService.isTokenValid(#authorization)")
    public ResponseEntity<?> registrarPagamentoCartao(
            final @RequestHeader("Authorization") String authorization,
            final @RequestBody RegistrarPagamentoRequestDTO pagamentoRequestDTO
    ) {

        final PagamentoDomainEntity pagamentoDomainEntity = PagamentoDomainEntity
                .builder()
                .cpf(pagamentoRequestDTO.getCpf())
                .numero(pagamentoRequestDTO.getNumero())
                .dataValidade(pagamentoRequestDTO.getDataValidade())
                .cvv(pagamentoRequestDTO.getCvv())
                .valor(pagamentoRequestDTO.getValor())
                .build();

        final PagamentoDomainEntity chavePagamento = registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CustomResponseDTO<RegistrarPagamentoResponseDTO>().setData(
                        RegistrarPagamentoResponseDTO.builder()
                                .chavePagamento(chavePagamento.getId())
                                .build()
                )
        );
    }

}
