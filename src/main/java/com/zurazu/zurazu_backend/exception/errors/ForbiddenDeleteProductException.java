package com.zurazu.zurazu_backend.exception.errors;

import com.zurazu.zurazu_backend.exception.ErrorCode;

public class ForbiddenDeleteProductException extends RuntimeException {

    public ForbiddenDeleteProductException(){
        super(ErrorCode.FORBIDDEN_DELETE_PRODUCT.getMessage());
    }

    public ForbiddenDeleteProductException(Exception ex){
        super(ex);
    }
}