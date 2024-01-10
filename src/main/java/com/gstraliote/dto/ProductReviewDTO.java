package com.gstraliote.dto;

import java.time.LocalDateTime;

public record ProductReviewDTO(
        Long id,
        String review,
        LocalDateTime postDate,
        Integer reviewRate,
        Long productId
) {
}
