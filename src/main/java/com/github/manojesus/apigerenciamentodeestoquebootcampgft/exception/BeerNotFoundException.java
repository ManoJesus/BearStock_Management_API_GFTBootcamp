package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

public class BeerNotFoundException extends Exception{
    public BeerNotFoundException(String name) {
        super("Beer with name "+name+" was not found");
    }
    public BeerNotFoundException(Long id) {
        super("Beer with ID "+id+" was not found");
    }
}
