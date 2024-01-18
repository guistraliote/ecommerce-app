package com.gstraliote.orderItems;

import java.io.Serial;
import java.io.Serializable;

public record OrderItemsDTO(
        Long id,
        Integer quantity,
        Long productId
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public void setProductId(Long id) {
    }
}