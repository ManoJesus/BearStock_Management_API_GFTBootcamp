package com.github.manojesus.apigerenciamentodeestoquebootcampgft.service;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerAlreadyExistsException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerNotFoundException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerQuantityLowerThanZero;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerStockExceededException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.mapper.BeerMapper;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.model.Beer;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerServiceImp implements BeerService{

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    @Override
    public List<BeerDTO> searchAllBeers() {
        List<Beer> foundBeers = new ArrayList<>();
        beerRepository.
                findAll().
                iterator().
                forEachRemaining(foundBeers::add);
        return foundBeers.stream()
                .map(beerMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public BeerDTO searchBeerByName(String name) throws BeerNotFoundException {
        Beer beerToReturn = beerRepository.findBeerByName(name).orElseThrow(() -> new BeerNotFoundException(name));
        return beerMapper.toDTO(beerToReturn);
    }

    @Override
    public BeerDTO create(BeerDTO beerDTOToSave) throws BeerAlreadyExistsException {
        verifyIfAlreadyExistsByName(beerDTOToSave.getName());
        Beer beerToSave = beerMapper.toModel(beerDTOToSave);
        return beerMapper.toDTO(beerRepository.save(beerToSave));
    }

    @Override
    public void deleteBeerById(Long id) throws BeerNotFoundException {
        verifyIfIdIsValid(id);
        beerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public BeerDTO incrementBeerQuantity(Long id, Integer quantityToIncrement) throws BeerNotFoundException, BeerStockExceededException {
        Beer toBeIncrementedBeer = verifyIfIdIsValid(id);
        int quantityAfterIncrement = quantityToIncrement + toBeIncrementedBeer.getQuantity();
        int maxStock = toBeIncrementedBeer.getMax();
        if(quantityAfterIncrement <= maxStock ){
            toBeIncrementedBeer.setQuantity(quantityAfterIncrement);
        }else {
            throw new BeerStockExceededException(id, quantityAfterIncrement, maxStock);
        }
        return beerMapper.toDTO(toBeIncrementedBeer);
    }
    @Transactional
    @Override
    public BeerDTO decrementBeerQuantity(Long id, Integer quantityToDecrement) throws BeerNotFoundException, BeerQuantityLowerThanZero {
        Beer toBeIncrementedBeer = verifyIfIdIsValid(id);
        int quantityAfterDecrement = toBeIncrementedBeer.getQuantity() + quantityToDecrement;
        if(quantityAfterDecrement >= 0 ){
            toBeIncrementedBeer.setQuantity(quantityAfterDecrement);
        }else {
            throw new BeerQuantityLowerThanZero(id, quantityAfterDecrement);
        }
        return beerMapper.toDTO(toBeIncrementedBeer);
    }

    private Beer verifyIfIdIsValid(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id)
                .orElseThrow(() ->  new BeerNotFoundException(id));

    }


    private void verifyIfAlreadyExistsByName(String name) throws BeerAlreadyExistsException {
        Optional<Beer> optionalBeer = beerRepository.findBeerByName(name);
        if(optionalBeer.isPresent()){
            throw new BeerAlreadyExistsException(name);
        }
    }
}
