package br.com.emerlopes.payments.application.entrypoint.rest.cartao;

import br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto.BuscaCartaoResponseDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto.GerarCartaoRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto.GerarCartaoResponseDTO;
import br.com.emerlopes.payments.application.shared.CustomResponseDTO;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.usecase.cartao.BuscarCartoesPorClienteUseCase;
import br.com.emerlopes.payments.domain.usecase.cartao.GerarCartaoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/cartoes")
public class CartaoController {

    private final GerarCartaoUseCase gerarCartaoUseCase;
    private final BuscarCartoesPorClienteUseCase buscarCartoesPorClienteUseCase;

    public CartaoController(
            final GerarCartaoUseCase gerarCartaoUseCase,
            final BuscarCartoesPorClienteUseCase buscarCartoesPorClienteUseCase
    ) {
        this.gerarCartaoUseCase = gerarCartaoUseCase;
        this.buscarCartoesPorClienteUseCase = buscarCartoesPorClienteUseCase;
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

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> buscarCartoesCliente(
            final @PathVariable String idCliente
    ) {
        final CartaoDomainEntity cartaoDomainEntity = CartaoDomainEntity
                .builder()
                .idCliente(UUID.fromString(idCliente))
                .build();

        final List<CartaoDomainEntity> cartaoGerado = buscarCartoesPorClienteUseCase.execute(cartaoDomainEntity);
        final List<BuscaCartaoResponseDTO> cartoes = cartaoGerado.stream().map(cartao -> BuscaCartaoResponseDTO.builder()
                .idCartao(cartao.getId().toString())
                .numero(cartao.getNumero())
                .build()
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                new CustomResponseDTO<List<BuscaCartaoResponseDTO>>().setData(cartoes)
        );
    }
}