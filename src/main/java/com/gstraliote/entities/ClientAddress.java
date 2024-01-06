package com.gstraliote.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "CLIENT_ADDRESS")
public class ClientAddress implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long Id;

    @Column(name = "ADDRESS_NAME")
    private String AddressName;

    @Column(name = "ADDRESS")
    private String Address;

    @Column(name = "ADDRESS_NUMBER")
    private Integer AddressNumber;

    @Column(name = "ADDRESS_POSTAL_CODE")
    private String PostalCode;

    @Column(name = "ADDRESS_CITY")
    private String City;

    @Column(name = "ADDRESS_STATE")
    private String State;

    @Column(name = "ADDRESS_COUNTRY")
    private String Country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @Column(name = "CLIENT_NAME")
    private String clientName;

    public String getClientName() {
        return client.getName();
    }

    public void setClientName (Client client) {
        this.client = client;
        this.clientName = client.getName();
    }
}
