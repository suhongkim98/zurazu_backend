package com.zurazu.zurazu_backend.exception.errors;

import com.zurazu.zurazu_backend.exception.ErrorCode;

public class RegisterFailException extends RuntimeException {

    public RegisterFailException(){
        super(ErrorCode.REGISTER_FAILED.getMessage());
    }

    public RegisterFailException(Exception ex){
        super(ex);
    }
}