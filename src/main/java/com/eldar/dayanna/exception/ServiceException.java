package com.eldar.dayanna.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private int errorCode;

    public ServiceException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
