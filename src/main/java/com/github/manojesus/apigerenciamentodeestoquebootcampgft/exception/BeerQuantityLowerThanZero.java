package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

public class BeerQuantityLowerThanZero extends Throwable {
    public BeerQuantityLowerThanZero(Long id, int quantityAfterDecrement) {
        super("Cannot decrement beer quantity to lower than 0.\n" +
                "Beer of ID "+ id +" has stock equal to "+quantityAfterDecrement);
    }
}
