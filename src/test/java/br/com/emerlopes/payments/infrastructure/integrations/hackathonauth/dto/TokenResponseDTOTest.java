package br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenResponseDTOTest {

    @Test
    void testSerialization() throws Exception {
        TokenResponseDTO responseDTO = TokenResponseDTO.builder()
                .tokenValido(true)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(responseDTO);

        String expectedJson = "{\"token_valido\":true}";
        assertEquals(expectedJson, jsonString);
    }

    @Test
    void testDeserialization() throws Exception {
        String jsonString = "{\"token_valido\":true}";

        ObjectMapper objectMapper = new ObjectMapper();
        TokenResponseDTO responseDTO = objectMapper.readValue(jsonString, TokenResponseDTO.class);

        assertEquals(true, responseDTO.isTokenValido());
    }
}