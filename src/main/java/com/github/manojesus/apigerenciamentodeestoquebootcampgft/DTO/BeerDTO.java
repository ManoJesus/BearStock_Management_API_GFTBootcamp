package com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.enums.BeerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BeerDTO {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String brand;
    @NotNull
    @NotEmpty
    private Integer max;
    @NotNull
    @NotEmpty
    private Integer quantity;
    @NotNull
    @NotEmpty
    private BeerType beerType;
}
