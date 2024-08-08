package br.com.emerlopes.payments.application.entrypoint.rest.pagamento;

import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.BuscarPagamentosCartaoResponseDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.RegistrarPagamentoRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.RegistrarPagamentoResponseDTO;
import br.com.emerlopes.payments.application.shared.CustomResponseDTO;
import br.com.emerlopes.payments.application.shared.enums.MetodoPagamentoEnum;
import br.com.emerlopes.payments.application.shared.enums.StatusPagamentoEnum;
import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.usecase.pagamento.BuscarPagamentosCartaoUseCase;
import br.com.emerlopes.payments.domain.usecase.pagamento.RegistrarPagamentoCartaoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final RegistrarPagamentoCartaoUseCase registrarPagamentoCartaoUseCase;
    private final BuscarPagamentosCartaoUseCase buscarPagamentosCartaoUseCase;

    public PagamentoController(
            final RegistrarPagamentoCartaoUseCase registrarPagamentoCartaoUseCase,
            final BuscarPagamentosCartaoUseCase buscarPagamentosCartaoUseCase
    ) {
        this.registrarPagamentoCartaoUseCase = registrarPagamentoCartaoUseCase;
        this.buscarPagamentosCartaoUseCase = buscarPagamentosCartaoUseCase;
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
                .descricao(
                        pagamentoRequestDTO.getDescricao() == null
                                ? "The Football Is Good For Training And Recreational Purposes"
                                : pagamentoRequestDTO.getDescricao()
                )
                .build();

        final PagamentoDomainEntity chavePagamento = registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);

        return ResponseEntity.status(HttpStatus.OK).body(
                RegistrarPagamentoResponseDTO.builder()
                        .chavePagamento(chavePagamento.getId())
                        .build()
        );
    }

    @GetMapping("/cliente/{cpf}")
    @PreAuthorize("@securityService.isTokenValid(#authorization)")
    public ResponseEntity<?> buscarPagamentosCartao(
            final @RequestHeader("Authorization") String authorization,
            final @PathVariable String cpf
    ) {

        final PagamentoDomainEntity pagamentoDomainEntity = PagamentoDomainEntity
                .builder()
                .cpf(cpf)
                .build();

        final List<PagamentoDomainEntity> pagamentos = buscarPagamentosCartaoUseCase.execute(pagamentoDomainEntity);

        return ResponseEntity.status(HttpStatus.OK).body(
                pagamentos.stream().map(
                                pag -> BuscarPagamentosCartaoResponseDTO.builder()
                                        .valor(pag.getValor())
                                        .descricao(pag.getDescricao())
                                        .metodoPagamento(MetodoPagamentoEnum.fromDescricao(pag.getMetodoPagamento()))
                                        .statusPagamento(StatusPagamentoEnum.fromDescricao(pag.getStatusPagamento()))
                                        .build())
                        .toList());
    }

}
