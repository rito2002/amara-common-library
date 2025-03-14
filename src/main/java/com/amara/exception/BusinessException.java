package com.amara.exception;

import lombok.Getter;

/**
 * Exception thrown when a business rule is violated.
 */
@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getTranslationKey());
        this.errorCode = errorCode;
    }
}
