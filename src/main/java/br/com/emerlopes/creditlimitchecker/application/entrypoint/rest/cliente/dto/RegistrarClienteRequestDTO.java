package br.com.emerlopes.creditlimitchecker.application.entrypoint.rest.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrarClienteRequestDTO {

    @NotNull(message = "CPF não pode ser nulo")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 digitos")
    private String cpf;

    @NotNull(message = "Nome nao pode ser nulo")
    @Size(min = 1, max = 100, message = "Nome deve ter entre 1 e 100 caracteres")
    private String nome;

    @NotNull(message = "Email não pode ser nulo")
    @Email(message = "Email deve ser valido")
    private String email;

    @NotNull(message = "Telefone não pode ser nulo")
    @Pattern(regexp = "\\d{11}", message = "Telefone deve conter 11 digitos")
    private String telefone;

    @NotNull(message = "Rua não pode ser nula")
    @Size(min = 1, max = 255, message = "Rua deve ter entre 1 e 255 caracteres")
    private String rua;

    @NotNull(message = "Cidade não pode ser nula")
    @Size(min = 1, max = 100, message = "Cidade deve ter entre 1 e 100 caracteres")
    private String cidade;

    @NotNull(message = "Estado não pode ser nulo")
    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    private String estado;

    @NotNull(message = "CEP nao pode ser nulo")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 digitos")
    private String cep;

    @NotNull(message = "Pais não pode ser nulo")
    @Size(min = 1, max = 100, message = "Pais deve ter entre 1 e 100 caracteres")
    private String pais;

}
