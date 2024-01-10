package com.gstraliote.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Table(name = "CATEGORY")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @NotNull(message = "A categoria não pode ser nula")
    @Column(name = "CATEGORY_NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "CATEGORY_PATH")
    private String path;
}
