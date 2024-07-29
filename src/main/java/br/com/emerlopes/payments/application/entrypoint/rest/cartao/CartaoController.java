package br.com.emerlopes.payments.application.entrypoint.rest.cartao;

import br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto.GerarCartaoRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto.GerarCartaoResponseDTO;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.usecase.cartao.GerarCartaoUseCase;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(CartaoController.class);
    private final GerarCartaoUseCase gerarCartaoUseCase;

    public CartaoController(
            final GerarCartaoUseCase gerarCartaoUseCase
    ) {
        this.gerarCartaoUseCase = gerarCartaoUseCase;
    }


    @PostMapping
    public ResponseEntity<?> criarCartao(
            final @RequestBody GerarCartaoRequestDTO cartaoRequestDTO
    ) {
        log.info("Recebendo requisicao para gerar cartao");
        final CartaoDomainEntity cartaoDomainEntity = CartaoDomainEntity
                .builder()
                .numero(cartaoRequestDTO.getNumero())
                .limite(cartaoRequestDTO.getLimite())
                .dataValidade(cartaoRequestDTO.getDataValidade())
                .cvv(cartaoRequestDTO.getCvv())
                .build();

        final CartaoDomainEntity idCartaoGerado = gerarCartaoUseCase.execute(cartaoDomainEntity);

        log.info("Cartao gerado com sucesso");

        return ResponseEntity.status(HttpStatus.OK).body(
                GerarCartaoResponseDTO.builder()
                        .idCartao(idCartaoGerado.getId())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> getCartao(
            final @PathVariable UUID cartaoId
    ) {
        log.info("Recebendo requisicao para buscar cartao");
        final CartaoDomainEntity cartaoDomainEntity = CartaoDomainEntity
                .builder()
                .id(cartaoId)
                .build();

        final CartaoDomainEntity idCartaoGerado = gerarCartaoUseCase.execute(cartaoDomainEntity);

        log.info("Cartao encontrado com sucesso");

        return ResponseEntity.status(HttpStatus.OK).body(
                GerarCartaoResponseDTO.builder()
                        .idCartao(idCartaoGerado.getId())
                        .build()
        );
    }
}