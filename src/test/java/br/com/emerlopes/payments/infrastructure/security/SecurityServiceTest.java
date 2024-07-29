package br.com.emerlopes.payments.infrastructure.security;

import br.com.emerlopes.payments.application.exceptions.InvalidTokenException;
import br.com.emerlopes.payments.application.shared.CustomResponseDTO;
import br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.HackathonAuthClient;
import br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.dto.TokenResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {

    @Mock
    private HackathonAuthClient hackathonAuthClient;

    @InjectMocks
    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        securityService = new SecurityService(hackathonAuthClient);
    }

    @Test
    void testIsTokenValid_Success() {
        TokenResponseDTO tokenResponseDTO = TokenResponseDTO.builder()
                .tokenValido(true)
                .build();

        CustomResponseDTO<TokenResponseDTO> responseDTO = new CustomResponseDTO<TokenResponseDTO>()
                .setData(tokenResponseDTO);

        Mockito.when(hackathonAuthClient.validateToken(any(String.class)))
                .thenReturn(responseDTO);

        boolean result = securityService.isTokenValid("valid-token");

        assertTrue(result);
    }

    @Test
    void testIsTokenValid_InvalidToken() {
        TokenResponseDTO tokenResponseDTO = TokenResponseDTO.builder()
                .tokenValido(false)
                .build();

        CustomResponseDTO<TokenResponseDTO> responseDTO = new CustomResponseDTO<TokenResponseDTO>()
                .setData(tokenResponseDTO);

        Mockito.when(hackathonAuthClient.validateToken(any(String.class)))
                .thenReturn(responseDTO);

        assertThrows(InvalidTokenException.class, () -> {
            securityService.isTokenValid("invalid-token");
        });
    }
}