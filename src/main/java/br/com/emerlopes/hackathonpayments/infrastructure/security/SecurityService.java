package br.com.emerlopes.hackathonpayments.infrastructure.security;

import br.com.emerlopes.hackathonpayments.application.exceptions.InvalidTokenException;
import br.com.emerlopes.hackathonpayments.infrastructure.integrations.hackathonauth.HackathonAuthClient;
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

        final var response = hackathonAuthClient.validateToken(token);

        log.info("Token validado");

        final var isValid = response.getData().isTokenValido();

        if (!isValid) {
            log.error("Token inválido");
            throw new InvalidTokenException();
        }

        return true;
    }
}
