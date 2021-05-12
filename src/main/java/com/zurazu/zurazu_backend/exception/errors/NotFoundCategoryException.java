package com.zurazu.zurazu_backend.exception.errors;

import com.zurazu.zurazu_backend.exception.ErrorCode;

public class NotFoundCategoryException extends RuntimeException {

    public NotFoundCategoryException(){
        super(ErrorCode.LOGIN_FAILED.getMessage());
    }

    public NotFoundCategoryException(Exception ex){
        super(ex);
    }
}