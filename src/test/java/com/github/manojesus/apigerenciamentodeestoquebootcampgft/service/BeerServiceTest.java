package com.github.manojesus.apigerenciamentodeestoquebootcampgft.service;


import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.builderDTO.BeerDTOBuilder;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerAlreadyExistsException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.mapper.BeerMapper;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.model.Beer;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.repository.BeerRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;

    @InjectMocks
    private BeerServiceImp beerService;

    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    @Test
    void whenBeerInformedItShouldBeCreated() throws BeerAlreadyExistsException {
        //given
        BeerDTO expectedResultBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer expectedSavedBeer = beerMapper.toModel(expectedResultBeerDTO);

        //when
        when(beerRepository.findBeerByName(expectedResultBeerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);
        //then
        BeerDTO beerCreated = beerService.create(expectedResultBeerDTO);

        assertThat(beerCreated.getId(), is(equalTo(expectedResultBeerDTO.getId())));
        assertThat(beerCreated.getName(), is(equalTo(expectedResultBeerDTO.getName())));
        assertThat(beerCreated.getBrand(), is(equalTo(expectedResultBeerDTO.getBrand())));

    }


}
