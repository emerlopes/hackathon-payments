package br.com.emerlopes.creditlimitchecker.application.shared;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomResponseDTO<T> {
    private T data;
}