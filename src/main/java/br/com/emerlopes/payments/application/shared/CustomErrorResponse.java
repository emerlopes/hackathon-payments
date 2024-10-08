package br.com.emerlopes.payments.application.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CustomErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
