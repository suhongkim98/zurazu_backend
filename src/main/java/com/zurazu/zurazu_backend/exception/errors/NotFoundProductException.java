package com.zurazu.zurazu_backend.exception.errors;

import com.zurazu.zurazu_backend.exception.ErrorCode;

public class NotFoundProductException extends RuntimeException{

    public NotFoundProductException(){
        super(ErrorCode.NOT_FOUND_PRODUCT.getMessage());
    }

    public NotFoundProductException(Exception ex){
        super(ex);
    }
}

