package com.github.manojesus.apigerenciamentodeestoquebootcampgft.repository;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.model.Beer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BeerRepository extends CrudRepository<Beer,Long> {
    Optional<Beer> findBeerByName(String name);
}
