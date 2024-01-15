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
    @Column(name = "USER_CREDENTIAL_ID")
    private Long id;

    @Column(name = "USER_LOGIN", nullable = false, unique = true)
    private String userLogin;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_TYPE")
    private String userType;

    public void setPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(plainPassword);
    }

    public boolean checkPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainPassword, this.password);
    }
}
