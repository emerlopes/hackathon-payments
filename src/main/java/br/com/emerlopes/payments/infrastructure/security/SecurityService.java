package br.com.emerlopes.payments.infrastructure.security;

import br.com.emerlopes.payments.application.exceptions.InvalidTokenException;
import br.com.emerlopes.payments.application.shared.CustomResponseDTO;
import br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.HackathonAuthClient;
import br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.dto.TokenResponseDTO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(SecurityService.class);

    private final HackathonAuthClient hackathonAuthClient;

    public SecurityService(
            final HackathonAuthClient hackathonAuthClient
    ) {
        this.hackathonAuthClient = hackathonAuthClient;
    }

    public boolean isTokenValid(
            final String token
    ) {
        log.info("Validando token");

        final TokenResponseDTO response = hackathonAuthClient.validateToken(token);

        log.info("Token validado");

        final var isValid = response.isTokenValido();

        if (!isValid) {
            log.error("Token inválido");
            throw new InvalidTokenException();
        }

        return true;
    }
}
