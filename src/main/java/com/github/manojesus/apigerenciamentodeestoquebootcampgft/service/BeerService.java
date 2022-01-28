package com.github.manojesus.apigerenciamentodeestoquebootcampgft.service;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerAlreadyExistsException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerNotFoundException;

public interface BeerService {
    Iterable<BeerDTO> searchAllBeers();
    BeerDTO searchBeerByName(String name) throws BeerNotFoundException;
    BeerDTO create(BeerDTO beerDTOToSave) throws BeerAlreadyExistsException;
    void deleteBeerById(Long id) throws BeerNotFoundException;
    BeerDTO updateBeer(Long id, BeerDTO beerDTOToBeUpdated) throws BeerNotFoundException;
}
