package com.gstraliote.dto;

import java.util.Date;

public record PaymentMethodDTO(
        Long id,
        String method,
        String brand,
        String cardNumber,
        String cardHolder,
        Date expirationDate,
        Integer securityCode,
        Boolean active,
        Long clientId
) {
}
