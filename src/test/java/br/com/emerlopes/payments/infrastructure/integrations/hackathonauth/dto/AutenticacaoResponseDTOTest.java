package br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutenticacaoResponseDTOTest {

    @Test
    void testSerialization() throws Exception {
        AutenticacaoResponseDTO responseDTO = AutenticacaoResponseDTO.builder()
                .tokenAcesso("test_token")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(responseDTO);

        String expectedJson = "{\"token_acesso\":\"test_token\"}";
        assertEquals(expectedJson, jsonString);
    }

    @Test
    void testDeserialization() throws Exception {
        String jsonString = "{\"token_acesso\":\"test_token\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        AutenticacaoResponseDTO responseDTO = objectMapper.readValue(jsonString, AutenticacaoResponseDTO.class);

        assertEquals("test_token", responseDTO.getTokenAcesso());
    }
}