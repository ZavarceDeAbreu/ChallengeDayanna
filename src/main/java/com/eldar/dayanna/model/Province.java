package com.eldar.dayanna.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "province")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "code_31662", nullable = false, length = 4, unique = true)
    private String code31662;

    public Province(@NotNull(message = "El Id provincia no puede ser nulo") Integer id) {
        this.id = id;
    }
}
