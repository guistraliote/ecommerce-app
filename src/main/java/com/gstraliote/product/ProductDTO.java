package com.gstraliote.product;

import java.time.LocalDateTime;
import java.util.Date;

public record ProductDTO(
        Long id,
        String name,
        String description,
        Double price,
        Integer stockQuantity,
        String brand,
        Double weight,
        Date addDate,
        LocalDateTime updateDate,
        Long categoryId,
        String productCategory,
        String productCategoryPath
) {
}
