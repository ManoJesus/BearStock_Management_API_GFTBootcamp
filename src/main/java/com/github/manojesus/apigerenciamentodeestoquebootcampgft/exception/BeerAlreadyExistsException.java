package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

public class BeerAlreadyExistsException extends Exception {
    public BeerAlreadyExistsException(String name) {
        super("Beer with name "+name+" already exists");
    }
}
