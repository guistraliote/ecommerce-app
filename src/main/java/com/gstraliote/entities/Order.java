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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    LocalDateTime orderDate = LocalDateTime.now();

    private String status;

    private double totalOrderValue;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Long getClientId() {
        return this.client.getId();
    }
}
