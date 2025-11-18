package com.example.multipartTest.exceptions;


public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
