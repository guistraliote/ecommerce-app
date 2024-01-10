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
public record ClientAddressDTO(
        Long id,
        String addressName,
        String address,
        Integer addressNumber,
        String postalCode,
        String city,
        String state,
        String country,
        String clientName
) {
}
