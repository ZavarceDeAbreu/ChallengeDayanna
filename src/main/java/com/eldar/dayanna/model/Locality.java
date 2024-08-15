package com.eldar.dayanna.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "locality")
public class Locality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "postal_code", columnDefinition = "SMALLINT")
    private Short postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", insertable = false, updatable = false)
    private Province province;
}
