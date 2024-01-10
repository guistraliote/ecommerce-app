package com.gstraliote.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
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
