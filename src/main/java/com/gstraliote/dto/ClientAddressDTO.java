package com.gstraliote.dto;

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
