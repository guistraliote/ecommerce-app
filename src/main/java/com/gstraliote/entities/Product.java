package com.gstraliote.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    public Long id;

    @NotBlank(message = "O nome do produto não pode ser nulo")
    private String name;

    private String description;

    @NotBlank(message = "O preço do produto não pode ser nulo")
    private Double price;

    private Integer stockQuantity;

    private String brand;

    private Double weight;

    private Date addDate;

    LocalDateTime updateDate = LocalDateTime.now();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn (name = "category_id"),
            @JoinColumn (name = "category_name")
    })
    private Category category;
}
