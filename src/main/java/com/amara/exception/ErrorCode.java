package com.amara.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Standardized error codes for Amara and RubiWell applications.
 * - `code`: Unique identifier for each API error (e.g., "VALIDATION_FAILED").
 * - `httpStatus`: Corresponding HTTP status code.
 * - `translationKey`: The key used for localization.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ─────────────────────────────────────────────
    // 1. GENERAL ERRORS (System-wide issues)
    // ─────────────────────────────────────────────
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "error.internal"),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", HttpStatus.SERVICE_UNAVAILABLE, "error.service.unavailable"),
    UNKNOWN_ERROR("UNKNOWN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "error.unknown"),

    // ─────────────────────────────────────────────
    // 2. VALIDATION & INPUT ERRORS
    // ─────────────────────────────────────────────
    VALIDATION_FAILED("VALIDATION_FAILED", HttpStatus.BAD_REQUEST, "error.validation.failed"),
    INVALID_REQUEST("INVALID_REQUEST", HttpStatus.BAD_REQUEST, "error.invalid.request"),
    UNSUPPORTED_MEDIA_TYPE("UNSUPPORTED_MEDIA_TYPE", HttpStatus.UNSUPPORTED_MEDIA_TYPE, "error.unsupported.media.type"),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED, "error.method.not.allowed"),

    // ─────────────────────────────────────────────
    // 3. AUTHENTICATION & SECURITY ERRORS
    // ─────────────────────────────────────────────
    UNAUTHORIZED("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "error.unauthorized"),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", HttpStatus.UNAUTHORIZED, "error.authentication.failed"),
    TOKEN_EXPIRED("TOKEN_EXPIRED", HttpStatus.UNAUTHORIZED, "error.token.expired"),
    ACCESS_DENIED("ACCESS_DENIED", HttpStatus.FORBIDDEN, "error.access.denied"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", HttpStatus.FORBIDDEN, "error.account.locked"),

    // ─────────────────────────────────────────────
    // 4. RESOURCE ERRORS (Data retrieval issues)
    // ─────────────────────────────────────────────
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, "error.resource.not.found"),
    RESOURCE_ALREADY_EXISTS("RESOURCE_ALREADY_EXISTS", HttpStatus.CONFLICT, "error.resource.exists"),
    DATA_INTEGRITY_VIOLATION("DATA_INTEGRITY_VIOLATION", HttpStatus.CONFLICT, "error.data.integrity.violation"),

    // ─────────────────────────────────────────────
    // 5. BUSINESS LOGIC ERRORS (Custom rules)
    // ─────────────────────────────────────────────
    BUSINESS_VALIDATION_FAILED("BUSINESS_VALIDATION_FAILED", HttpStatus.BAD_REQUEST, "error.business.validation.failed"),
    OPERATION_NOT_PERMITTED("OPERATION_NOT_PERMITTED", HttpStatus.FORBIDDEN, "error.operation.not.permitted"),

    // ─────────────────────────────────────────────
    // 6. RATE LIMITING & NETWORK ISSUES
    // ─────────────────────────────────────────────
    TOO_MANY_REQUESTS("TOO_MANY_REQUESTS", HttpStatus.TOO_MANY_REQUESTS, "error.too.many.requests"),
    NETWORK_FAILURE("NETWORK_FAILURE", HttpStatus.SERVICE_UNAVAILABLE, "error.network.failure"),

    // ─────────────────────────────────────────────
    // 7. EXTERNAL SERVICE ERRORS (3rd Party APIs)
    // ─────────────────────────────────────────────
    EXTERNAL_SERVICE_FAILURE("EXTERNAL_SERVICE_FAILURE", HttpStatus.BAD_GATEWAY, "error.external.service.failure"),
    EXTERNAL_SERVICE_TIMEOUT("EXTERNAL_SERVICE_TIMEOUT", HttpStatus.GATEWAY_TIMEOUT, "error.external.service.timeout");

    private final String code;
    private final HttpStatus httpStatus;
    private final String translationKey;

    @Override
    public String toString() {
        return String.format("[%s] %s", httpStatus.value(), code);
    }
}
