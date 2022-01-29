package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends Exception{
    public BeerNotFoundException(String name) {
        super("Beer with name "+name+" was not found");
    }
    public BeerNotFoundException(Long id) {
        super("Beer with ID "+id+" was not found");
    }
}
