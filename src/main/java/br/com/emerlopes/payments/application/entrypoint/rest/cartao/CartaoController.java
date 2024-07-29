package br.com.emerlopes.payments.application.entrypoint.rest.cartao;

import br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto.GerarCartaoRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto.GerarCartaoResponseDTO;
import br.com.emerlopes.payments.application.shared.CustomResponseDTO;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.usecase.cartao.GerarCartaoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("api/cartoes")
public class CartaoController {

    private final GerarCartaoUseCase gerarCartaoUseCase;

    public CartaoController(
            final GerarCartaoUseCase gerarCartaoUseCase
    ) {
        this.gerarCartaoUseCase = gerarCartaoUseCase;
    }


    @PostMapping
    @PreAuthorize("@securityService.isTokenValid(#authorization)")
    public ResponseEntity<?> gerarCartao(
            final @RequestHeader("Authorization") String authorization,
            final @RequestBody GerarCartaoRequestDTO cartaoRequestDTO
    ) {
        final CartaoDomainEntity cartaoDomainEntity = CartaoDomainEntity
                .builder()
                .cpf(cartaoRequestDTO.getCpf())
                .numero(cartaoRequestDTO.getNumero())
                .limite(cartaoRequestDTO.getLimite())
                .dataValidade(cartaoRequestDTO.getDataValidade())
                .cvv(cartaoRequestDTO.getCvv())
                .build();

        final CartaoDomainEntity idCartaoGerado = gerarCartaoUseCase.execute(cartaoDomainEntity);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CustomResponseDTO<>().setData(
                        GerarCartaoResponseDTO.builder()
                                .idCartao(idCartaoGerado.getId())
                                .build()
                )
        );
    }

    @GetMapping
    public ResponseEntity<?> getCartao(
            final @PathVariable UUID cartaoId
    ) {
        final CartaoDomainEntity cartaoDomainEntity = CartaoDomainEntity
                .builder()
                .id(cartaoId)
                .build();

        final CartaoDomainEntity idCartaoGerado = gerarCartaoUseCase.execute(cartaoDomainEntity);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CustomResponseDTO<>().setData(
                        GerarCartaoResponseDTO.builder()
                                .idCartao(idCartaoGerado.getId())
                                .build()
                )
        );
    }
}