package com.github.manojesus.apigerenciamentodeestoquebootcampgft.service;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerAlreadyExistsException;

public interface BeerService {
    Iterable<BeerDTO> searchAllBeers();
    BeerDTO searchBeerById(Long id);
    BeerDTO searchBeerByName(String name) ;
    BeerDTO create(BeerDTO beerDTOToSave) throws BeerAlreadyExistsException;
    void deleteBeerById(Long id) ;
    BeerDTO updateBeer(Long id, BeerDTO beerDTOToBeUpdated);
}
