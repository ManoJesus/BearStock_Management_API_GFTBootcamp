package com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BeerNotFoundException.class)
    public ResponseEntity<String> beerNotFoundException(BeerNotFoundException beerNotFoundException){
        return new ResponseEntity<>(beerNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = BeerAlreadyExistsException.class)
    public ResponseEntity<String> beerAlreadyExistsException(BeerAlreadyExistsException beerAlreadyExistsException){
        return new ResponseEntity<>(beerAlreadyExistsException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = BeerStockExceededException.class)
    public ResponseEntity<String> beerStockExceededException(BeerStockExceededException beerStockExceededException){
        return new ResponseEntity<>(beerStockExceededException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = BeerQuantityLowerThanZero.class)
    public ResponseEntity<String> beerQuantityLowerThanZero(BeerQuantityLowerThanZero beerQuantityLowerThanZero){
        return new ResponseEntity<>(beerQuantityLowerThanZero.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
