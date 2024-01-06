package com.gstraliote.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_METHOD_ID")
    private Long id;

    @Column(name = "PAYMENT_METHOD")
    @NotBlank(message = "O método não pode ser nulo")
    private String method;

    @Column(name = "CREDIT_CARD_BRAND")
    private String brand;

    @CreditCardNumber
    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "CARD_HOLDER")
    private String cardHolder;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @Column(name = "SECURITY_CODE")
    private Integer securityCode;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;
}
