package com.zurazu.zurazu_backend.exception.errors;

import com.zurazu.zurazu_backend.exception.ErrorCode;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(){
        super(ErrorCode.LOGIN_FAILED.getMessage());
    }

    public LoginFailedException(Exception ex){
        super(ex);
    }
}