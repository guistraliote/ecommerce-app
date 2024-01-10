package com.gstraliote.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
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
