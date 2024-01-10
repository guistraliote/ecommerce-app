package com.gstraliote.dto;

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
public record UserCredentialsDTO(
        Long id,
        String userLogin,
        String userEmail,
        Long clientId
) {
}
