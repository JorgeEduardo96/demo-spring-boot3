package pt.com.example.springboot3.dtos;

import jakarta.validation.constraints.NotBlank;

public record MunicipioRecord(@NotBlank String codigo, @NotBlank String nif, @NotBlank String rua,
                              @NotBlank String localidade, @NotBlank String codigopostal) {
}
