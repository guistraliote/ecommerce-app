package com.gstraliote.client;

import com.gstraliote.clientAddress.ClientAddress;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "CLIENT")
public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "CLIENT_NAME")
    private String name;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve ter 11 dígitos numéricos")
    @Column(name = "CLIENT_CPF")
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Column(name = "CLIENT_EMAIL")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Column(name = "CLIENT_PHONE")
    private String phone;

    @NotNull
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ClientAddress> addresses = new ArrayList<>();
}