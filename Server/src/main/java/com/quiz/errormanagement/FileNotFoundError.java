package com.quiz.errormanagement;


public class FileNotFoundError extends RuntimeException {
    public FileNotFoundError(String message){
        super(message);
    }
    public FileNotFoundError(String message,Throwable e){
        super(message,e);
    }
}
