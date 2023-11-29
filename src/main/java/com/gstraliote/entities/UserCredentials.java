package com.gstraliote.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userLogin;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public void setPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(plainPassword);
    }

    public boolean checkPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainPassword, this.password);
    }

    public String getClientEmail() {
        return this.client.getEmail();
    }

    public Long getClientId() {
        return this.client.getId();
    }
}
