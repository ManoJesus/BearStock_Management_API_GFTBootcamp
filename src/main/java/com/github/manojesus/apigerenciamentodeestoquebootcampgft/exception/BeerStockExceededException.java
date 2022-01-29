package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class BeerStockExceededException extends Exception {
    public BeerStockExceededException(Long id, int quantityAfterIncrement, int maxStock) {
        super("Beer with ID "+id+" has limit stock for "+maxStock+" and the quantity added would be "+ quantityAfterIncrement);
    }
}
