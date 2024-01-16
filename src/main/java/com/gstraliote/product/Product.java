package com.gstraliote.product;

import com.gstraliote.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "PRODUCT")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    public Long id;

    @NotBlank(message = "O nome do produto não pode ser nulo")
    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String description;

    @NotNull(message = "O preço do produto não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço do produto deve ser maior que zero")
    @Column(name = "PRODUCT_PRICE")
    private Double price;

    @Column(name = "STOCK_QUANTITY")
    private Integer stockQuantity;

    @Column(name = "PRODUCT_BRAND")
    private String brand;

    @Column(name = "PRODUCT_WEIGHT")
    private Double weight;

    @Column(name = "PRODUCT_ADD_DATE")
    private Date addDate;

    @Column(name = "PRODUCT_UPDATE_DATE")
    LocalDateTime updateDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "CATEGORY_ID")
    private Category category;

    @Column(name = "CATEGORY_NAME")
    private String productCategory;

    @Column(name = "CATEGORY_PATH")
    private String productCategoryPath;

    public String getProductCategory() {
        return category.getName();
    }

    public String getProductCategoryPath() {
        return category.getPath();
    }

    public void setCategoryDetails(Category category) {
        this.category = category;
        this.productCategory = category.getName();
        this.productCategoryPath = category.getPath();
    }
}
