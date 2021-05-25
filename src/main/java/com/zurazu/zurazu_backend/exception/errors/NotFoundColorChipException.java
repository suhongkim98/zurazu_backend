package com.zurazu.zurazu_backend.exception.errors;

import com.zurazu.zurazu_backend.exception.ErrorCode;

public class NotFoundColorChipException extends RuntimeException{

    public NotFoundColorChipException(){
        super(ErrorCode.NOT_FOUND_COLOR_CHIP.getMessage());
    }

    public NotFoundColorChipException(Exception ex){
        super(ex);
    }
}

