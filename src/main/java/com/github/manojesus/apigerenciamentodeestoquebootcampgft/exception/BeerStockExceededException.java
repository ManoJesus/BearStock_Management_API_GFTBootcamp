package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

public class BeerStockExceededException extends Exception {
    public BeerStockExceededException(Long id, int quantityAfterIncrement, int maxStock) {
        super("Beer with ID "+id+" has limit stock for "+maxStock+" and the quantity added would be "+ quantityAfterIncrement);
    }
}
