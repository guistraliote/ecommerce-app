package com.gstraliote.orderItems;

public record OrderItemsDTO(
        Long id,
        Integer quantity,
        Long productId,
        String productName,
        Double productPrice
) {
}
