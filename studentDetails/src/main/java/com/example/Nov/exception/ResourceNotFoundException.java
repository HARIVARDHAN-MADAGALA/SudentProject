package com.example.Nov.exception;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message){
         super(message);
     }
}
