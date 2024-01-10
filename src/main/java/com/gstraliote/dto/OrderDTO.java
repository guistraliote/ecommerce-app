package com.gstraliote.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public record OrderDTO(
        Integer id,
        LocalDateTime orderDate,
        String status,
        double totalOrderValue,
        Long clientId,
        List<OrderItemsDTO> orderItems
) {
}
