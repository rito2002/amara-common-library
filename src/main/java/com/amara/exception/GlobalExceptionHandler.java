package com.amara.exception;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

/**
 * Global exception handler for Amara applications.
 * Handles different types of exceptions and provides localized error messages.
 */
@Slf4j
@Setter
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * Retrieves the localized error message based on the user's locale.
     * Falls back to English if no translation is available.
     *
     * @param errorCode The error code enum.
     * @param locale    The requested locale.
     * @return The translated error message.
     */
    private String translateMessage(ErrorCode errorCode, Locale locale) {
        return messageSource.getMessage(
                errorCode.getTranslationKey(),
                null,
                locale
        );
    }

    /**
     * Handles business-related exceptions resulting from validation or domain rule violations.
     */
    @ExceptionHandler(BusinessException.class)
    public ApiErrorResponse handleBusinessException(BusinessException ex, Locale locale) {
        log.warn("Business exception: {} - {}", ex.getErrorCode().getCode(), ex.getMessage());
        String message = translateMessage(ex.getErrorCode(), locale);
        return new ApiErrorResponse(
                String.valueOf(ex.getErrorCode().getHttpStatus().value()),
                ex.getErrorCode().getTranslationKey(),
                message
        );
    }

    /**
     * Handles infrastructure and system failures such as database issues.
     */
    @ExceptionHandler(TechnicalException.class)
    public ApiErrorResponse handleTechnicalException(TechnicalException ex, Locale locale) {
        log.error("Technical exception: {} - {}", ex.getErrorCode().getCode(), ex.getMessage(), ex);
        String message = translateMessage(ex.getErrorCode(), locale);
        return new ApiErrorResponse(
                String.valueOf(ex.getErrorCode().getHttpStatus().value()),
                ex.getErrorCode().getTranslationKey(),
                message
        );
    }

    /**
     * Handles failures in external services such as payment providers or third-party APIs.
     */
    @ExceptionHandler(ExternalServiceException.class)
    public ApiErrorResponse handleExternalServiceException(ExternalServiceException ex, Locale locale) {
        log.warn("External service failure: {} - {}", ex.getErrorCode().getCode(), ex.getMessage());
        String message = translateMessage(ex.getErrorCode(), locale);
        return new ApiErrorResponse(
                String.valueOf(ex.getErrorCode().getHttpStatus().value()),
                ex.getErrorCode().getTranslationKey(),
                message
        );
    }

    /**
     * Handles all uncaught exceptions that are not explicitly handled elsewhere.
     */
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse handleGenericException(Exception ex, Locale locale) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        String message = messageSource.getMessage("error.internal", null, "An internal server error occurred.", locale);
        return new ApiErrorResponse(
                String.valueOf(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value()),
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                message
        );
    }
}
