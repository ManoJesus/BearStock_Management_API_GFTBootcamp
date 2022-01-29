package com.github.manojesus.apigerenciamentodeestoquebootcampgft.service;


import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.builderDTO.BeerDTOBuilder;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerAlreadyExistsException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerNotFoundException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerQuantityLowerThanZero;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerStockExceededException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.mapper.BeerMapper;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.model.Beer;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    private final long INVALID_ID = 20L;

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

    @Test
    void whenBeerInformedAlreadyRegisteredItShouldThrowAnException(){
        //given
        BeerDTO expectedResultBeer = BeerDTOBuilder.builder().build().buildDTO();
        Beer duplicatedBeer = beerMapper.toModel(expectedResultBeer);

        //when
        when(beerRepository.findBeerByName(expectedResultBeer.getName())).thenReturn(Optional.of(duplicatedBeer));

        //then
        assertThrows(BeerAlreadyExistsException.class, () -> beerService.create(expectedResultBeer));
    }

    @Test
    void whenValidNameBeerIsGivenThenReturnBeer() throws BeerNotFoundException {
        //given
        BeerDTO expectedResultBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer expectedFoundBeer = beerMapper.toModel(expectedResultBeerDTO);

        //when
        when(beerRepository.findBeerByName(expectedResultBeerDTO.getName())).thenReturn(Optional.of(expectedFoundBeer));

        //then
        BeerDTO foundBeerDTO = beerService.searchBeerByName(expectedResultBeerDTO.getName());
        assertThat(foundBeerDTO, is(equalTo(expectedResultBeerDTO)));
    }
    @Test
    void whenNameNotRegisteredIsGivenThenThrowException() throws BeerNotFoundException {
        //given
        BeerDTO expectedResultBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        //when
        when(beerRepository.findBeerByName(expectedResultBeerDTO.getName())).thenReturn(Optional.empty());
        //then
        assertThrows(BeerNotFoundException.class, () -> beerService.searchBeerByName(expectedResultBeerDTO.getName()));
    }
    @Test
    void whenListBeerIsCalledThenAListOfAllBeersIsReturned(){
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
        //when
        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedBeer));

        //then
        List<BeerDTO> foundBeersDTO = beerService.searchAllBeers();
        assertThat(foundBeersDTO, is(not(empty())));
        assertThat(foundBeersDTO.get(0), is(equalTo(expectedBeerDTO)));
    }
    @Test
    void whenSearchBeersIsCalledThenReturnAnEmptyList(){
        //when
        when(beerRepository.findAll()).thenReturn(Collections.emptyList());

        //then

        List<BeerDTO> foundBeers = beerService.searchAllBeers();

        assertThat(foundBeers,is(empty()));
    }
    @Test
    void whenDeleteIsCalledWithIdThenBeerItMustBeDeleted() throws BeerNotFoundException {
        //given
        BeerDTO expectedDeleteBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer expectedDeleteBeer = beerMapper.toModel(expectedDeleteBeerDTO);
        //when
        when(beerRepository.findById(expectedDeleteBeer.getId())).thenReturn(Optional.of(expectedDeleteBeer));
        doNothing().when(beerRepository).deleteById(expectedDeleteBeer.getId());

        //then
        beerService.deleteBeerById(expectedDeleteBeer.getId());

        verify(beerRepository, times(1)).findById(expectedDeleteBeerDTO.getId());
        verify(beerRepository, times(1)).deleteById(expectedDeleteBeerDTO.getId());
    }
    @Test
    void whenDeleteIsCalledWithNotRegisteredIdThenThrowsException() throws BeerNotFoundException {
        //given
        BeerDTO expectedDeleteBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        //when
        when(beerRepository.findById(expectedDeleteBeerDTO.getId())).thenReturn(Optional.empty());
        //then
        assertThrows(BeerNotFoundException.class, () ->beerService.deleteBeerById(expectedDeleteBeerDTO.getId()));
    }
    @Test
    void whenUpdateIsCalledWithValidIdThenItShouldReturnUpdatedBeer() throws BeerNotFoundException {
        //given
        BeerDTO toBeUpdatedBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer toBeUpdatedBeer = beerMapper.toModel(toBeUpdatedBeerDTO);

        toBeUpdatedBeerDTO.setName("Skol");

        //when
        when(beerRepository.findById(toBeUpdatedBeer.getId())).thenReturn(Optional.of(toBeUpdatedBeer));

        //then
        BeerDTO updatedBeerDTO = beerService.updateBeer(toBeUpdatedBeer.getId(), toBeUpdatedBeerDTO);
        assertThat(updatedBeerDTO.getName(), is(equalTo(toBeUpdatedBeerDTO.getName())));
    }

    @Test
    void whenIncrementIsCalledThenBeerStockMustBeIncremented() throws BeerNotFoundException, BeerStockExceededException {
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedBeerDTO.getQuantity() + quantityToIncrement;

        //then
        BeerDTO beerDTOIncremented = beerService.incrementBeerQuantity(expectedBeerDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, is(equalTo(beerDTOIncremented.getQuantity())));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedBeerDTO.getMax()));
    }

    @Test
    void whenIncrementValueIsGreaterThanMaxStockThenThrowException(){
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        Long id = expectedBeerDTO.getId();
        when(beerRepository.findById(id)).thenReturn(Optional.of(expectedBeer));

        //then
        int quantityToBeIncremented = 80;
        assertThrows(BeerStockExceededException.class,() -> beerService.incrementBeerQuantity(id, quantityToBeIncremented));
    }
    @Test
    void whenIncrementSumIsGraterThanMaxStockThenThrowException(){
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        Long id = expectedBeerDTO.getId();
        when(beerRepository.findById(id)).thenReturn(Optional.of(expectedBeer));

        //then
        int quantityToBeIncremented = 48;
        assertThrows(BeerStockExceededException.class,() -> beerService.incrementBeerQuantity(id, quantityToBeIncremented));

    }
    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException(){
        //when
        when(beerRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        //then
        int quantityToIncrement = 10;

        assertThrows(BeerNotFoundException.class, () -> beerService.incrementBeerQuantity(INVALID_ID, quantityToIncrement));
    }
    @Test
    void whenDecrementIsCalledThanBeerStockMustBeDecremented() throws BeerQuantityLowerThanZero, BeerNotFoundException {
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        int quantityToDecrement= 5;
        int expectedQuantityAfterDecrement = expectedBeerDTO.getQuantity() - quantityToDecrement;

        //then
        BeerDTO beerDTOIncremented = beerService.decrementBeerQuantity(expectedBeerDTO.getId(), quantityToDecrement);

        assertThat(expectedQuantityAfterDecrement, is(equalTo(beerDTOIncremented.getQuantity())));
        assertThat(expectedQuantityAfterDecrement, lessThan(expectedBeerDTO.getMax()));
    }
}
