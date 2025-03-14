package com.amara.exception;

import lombok.Getter;

/**
 * Exception thrown for technical errors like database failures or system malfunctions.
 */
@Getter
public class TechnicalException extends RuntimeException {
    private final ErrorCode errorCode;

    public TechnicalException(ErrorCode errorCode) {
        super(errorCode.getTranslationKey());
        this.errorCode = errorCode;
    }
}
