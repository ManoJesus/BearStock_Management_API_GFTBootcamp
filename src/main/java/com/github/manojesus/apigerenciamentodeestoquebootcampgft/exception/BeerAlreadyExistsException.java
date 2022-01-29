package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerAlreadyExistsException extends Exception {
    public BeerAlreadyExistsException(String name) {
        super("Beer with name "+name+" already exists");
    }
}
