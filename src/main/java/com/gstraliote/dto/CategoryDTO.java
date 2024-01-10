package com.gstraliote.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
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
