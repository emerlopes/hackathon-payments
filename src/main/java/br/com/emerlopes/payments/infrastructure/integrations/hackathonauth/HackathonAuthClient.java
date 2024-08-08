package br.com.emerlopes.payments.infrastructure.integrations.hackathonauth;

import br.com.emerlopes.payments.application.shared.CustomResponseDTO;
import br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.dto.AutenticacaoResponseDTO;
import br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.dto.TokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hackathonauth", url = "${application.hackathonauth.host}")
public interface HackathonAuthClient {
    @PostMapping("/auth/token")
    CustomResponseDTO<AutenticacaoResponseDTO> getToken(
            final @RequestParam String username,
            final @RequestParam String password,
            final @RequestParam("client_secret") String clientSecret
    );

    @PostMapping("/auth/validate")
    TokenResponseDTO validateToken(
            final @RequestHeader("Authorization") String accessToken
    );
}
