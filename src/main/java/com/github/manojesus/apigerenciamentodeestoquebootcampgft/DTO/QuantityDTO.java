package com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuantityDTO {
    @NotNull
    @Max(150)
    private Integer quantity;
}
