package com.amara.exception;

import lombok.Getter;

/**
 * Exception thrown when an external service (e.g., payment provider) fails.
 */
@Getter
public class ExternalServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public ExternalServiceException(ErrorCode errorCode) {
        super(errorCode.getTranslationKey());
        this.errorCode = errorCode;
    }
}
