package com.gstraliote.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public record ProductReviewDTO(
        Long id,
        String review,
        LocalDateTime postDate,
        Integer reviewRate,
        Long productId
) {
}
