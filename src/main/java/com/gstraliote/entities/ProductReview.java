package com.gstraliote.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    LocalDateTime postDate = LocalDateTime.now();

    private Integer reviewRate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn (name = "product_id"),
            @JoinColumn (name = "product_name")
    })
    private Product product;
}
