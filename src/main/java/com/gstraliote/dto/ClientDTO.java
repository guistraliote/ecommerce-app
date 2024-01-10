package com.gstraliote.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClientDTO(
        Long id,
        @NotBlank(message = "O nome é obrigatório")
        String name,
        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve ter 11 dígitos numéricos")
        String cpf,
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,
        @NotBlank(message = "O telefone é obrigatório")
        String phone,
        @NotNull
        Boolean active
) {
}
