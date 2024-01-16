package com.gstraliote.productReview;

import com.gstraliote.product.Product;
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
    @Column(name = "PRODUCT_REVIEW_ID")
    private Long id;

    @Column(name = "PRODUCT_REVIEW")
    private String review;

    @Column(name = "REVIEW_POST_DATE")
    LocalDateTime postDate = LocalDateTime.now();

    @Column(name = "REVIEW_RATE_ID")
    private Integer reviewRate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "PRODUCT_NAME")
    private Product product;
}
