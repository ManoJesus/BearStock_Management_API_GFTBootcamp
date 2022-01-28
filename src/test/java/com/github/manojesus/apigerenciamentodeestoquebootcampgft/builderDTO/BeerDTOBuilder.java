package com.github.manojesus.apigerenciamentodeestoquebootcampgft.builderDTO;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.enums.BeerType;
import lombok.Builder;

@Builder
public class BeerDTOBuilder {
    private Long id = 1L;

    private String name = "Brahma";

    private String brand = "Ambev";

    private Integer max = 50;

    private Integer quantity = 10;

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
