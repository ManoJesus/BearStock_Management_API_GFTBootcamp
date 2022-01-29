package com.github.manojesus.apigerenciamentodeestoquebootcampgft.builderDTO;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.enums.BeerType;
import lombok.Builder;

@Builder
public class BeerDTOBuilder {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String name = "Brahma";
    @Builder.Default
    private String brand = "Ambev";
    @Builder.Default
    private Integer max = 50;
    @Builder.Default
    private Integer quantity = 10;
    @Builder.Default
    private BeerType beerType = BeerType.LAGER;

    public BeerDTO buildDTO(){
        return new BeerDTO(
                id,
                name,
                brand,
                max,
                quantity,
                beerType
        );
    }

}
