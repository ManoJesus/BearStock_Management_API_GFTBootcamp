package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerQuantityLowerThanZero extends Exception {
    public BeerQuantityLowerThanZero(Long id, int quantityAfterDecrement) {
        super("Cannot decrement beer quantity to lower than 0.\n" +
                "Beer of ID "+ id +" has stock equal to "+quantityAfterDecrement);
    }
}
