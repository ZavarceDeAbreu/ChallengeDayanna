package com.eldar.dayanna.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalityDto {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotNull(message = "El codigo postal es requerido")
    private Short postalCode;

    @NotNull(message = "El Id provincia no puede ser nulo")
    private Integer provinceId;

    private String filePath;

}
