package com.common.exceptions;

public class IncorrectScoreException extends Exception {
    public IncorrectScoreException(){
        super("the score should be between 0 and 3");
    }
    public IncorrectScoreException(String message){
        super(message);
    }
}
