package com.gstraliote.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO(
        @NotNull(message = "A categoria não pode ser nula")
        Long id,
        @NotBlank(message = "O nome da categoria não pode estar em branco")
        String name,
        @NotNull
        Boolean active,
        String path
) {
}