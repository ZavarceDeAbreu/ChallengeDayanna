package com.eldar.dayanna.model.dto;

import com.eldar.dayanna.model.Province;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDto {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotBlank(message = "El codigo31662 es requerido")
    @Pattern(regexp = "^\\d{4}$", message = "El código debe tener 4 dígitos")
    private String code31662;

    private String filePath;

    public ProvinceDto(String name, String code31662) {
        this.name = name;
        this.code31662 = code31662;
    }

    public static ProvinceDto fromEntity(Province entity) {
        return new ProvinceDto(
                entity.getName(),
                entity.getCode31662()
        );
    }
}
