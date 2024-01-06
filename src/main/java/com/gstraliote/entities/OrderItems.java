package com.gstraliote.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @JoinTable(name = "ORDER_ITEM_PRODUCT",
            joinColumns = @JoinColumn(name = "ORDER_ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_PRICE")
    private Double productPrice;

    public String getProductName() {
        return product.getName();
    }

    public Double getProductPrice() {
        return product.getPrice();
    }

    public void setProductDetails(Product product) {
        this.product = product;
        this.productName = product.getName();
        this.productPrice = product.getPrice();
    }
}
