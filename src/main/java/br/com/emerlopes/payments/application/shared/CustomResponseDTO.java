package br.com.emerlopes.payments.application.shared;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomResponseDTO<T> {
    private T data;
}