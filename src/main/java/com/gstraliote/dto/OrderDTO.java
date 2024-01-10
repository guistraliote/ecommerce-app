package com.gstraliote.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        Integer id,
        LocalDateTime orderDate,
        String status,
        double totalOrderValue,
        Long clientId,
        List<OrderItemsDTO> orderItems
) {
}
