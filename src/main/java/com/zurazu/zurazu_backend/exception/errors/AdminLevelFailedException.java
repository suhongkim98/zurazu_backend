package com.zurazu.zurazu_backend.exception.errors;

import com.zurazu.zurazu_backend.exception.ErrorCode;

public class AdminLevelFailedException extends RuntimeException {

    public AdminLevelFailedException(){
        super(ErrorCode.ADMIN_LEVEL_FAILED.getMessage());
    }

    public AdminLevelFailedException(Exception ex){
        super(ex);
    }
}
