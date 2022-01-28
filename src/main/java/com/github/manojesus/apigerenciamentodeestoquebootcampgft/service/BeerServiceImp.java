package com.github.manojesus.apigerenciamentodeestoquebootcampgft.service;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerAlreadyExistsException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.mapper.BeerMapper;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.model.Beer;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerServiceImp implements BeerService{

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    @Override
    public Iterable<BeerDTO> searchAllBeers() {
        return null;
    }

    @Override
    public BeerDTO searchBeerById(Long id) {
        return null;
    }

    @Override
    public BeerDTO searchBeerByName(String name) {
        return null;
    }

    @Override
    public BeerDTO create(BeerDTO beerDTOToSave) throws BeerAlreadyExistsException {
        verifyIfAlreadyExistsByName(beerDTOToSave.getName());
        Beer beerToSave = beerMapper.toModel(beerDTOToSave);
        Beer beerSaved = beerRepository.save(beerToSave);
        return beerMapper.toDTO(beerSaved);
    }

    @Override
    public void deleteBeerById(Long id) {

    }

    @Override
    public BeerDTO updateBeer(Long id, BeerDTO beerDTOToBeUpdated) {
        return null;
    }

    private void verifyIfAlreadyExistsByName(String name) throws BeerAlreadyExistsException {
        Optional<Beer> optionalBeer = beerRepository.findBeerByName(name);
        if(optionalBeer.isPresent()){
            throw new BeerAlreadyExistsException(name);
        }
    }
}
