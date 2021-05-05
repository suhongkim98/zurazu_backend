package com.zurazu.zurazu_backend.core.security;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}
