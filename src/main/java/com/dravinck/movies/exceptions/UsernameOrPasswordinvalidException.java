package com.dravinck.movies.exceptions;

public class UsernameOrPasswordinvalidException extends RuntimeException{

    public UsernameOrPasswordinvalidException(String message) {
        super(message);
    }
}
