package com.zurazu.zurazu_backend.exception.errors;

import com.zurazu.zurazu_backend.exception.ErrorCode;

public class NotFoundTypeException extends RuntimeException{

    public NotFoundTypeException(){
        super(ErrorCode.NOT_FOUND_TYPE.getMessage());
    }

    public NotFoundTypeException(Exception ex){
        super(ex);
    }
}
