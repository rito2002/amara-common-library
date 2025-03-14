package com.amara.exception;

import org.easymock.EasyMock;
import org.springframework.context.MessageSource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;

import static org.testng.Assert.assertEquals;

/**
 * Comprehensive tests for the GlobalExceptionHandler.
 * Uses EasyMock + TestNG to check different exceptions and locales.
 */
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private MessageSource messageSource;

    @BeforeMethod
    public void setUp() {
        // Create the mock for the MessageSource
        messageSource = EasyMock.createMock(MessageSource.class);
        // Initialize the GlobalExceptionHandler with the mocked MessageSource
        globalExceptionHandler = new GlobalExceptionHandler();
        globalExceptionHandler.setMessageSource(messageSource);
    }

    // ─────────────────────────────────────────────────────────────────
    // 1. BUSINESS EXCEPTION
    // ─────────────────────────────────────────────────────────────────

    @Test
    public void testHandleBusinessExceptionEnglish() {
        // Given
        BusinessException exception = new BusinessException(ErrorCode.INVALID_REQUEST);

        // Expecting an English message from the messageSource
        EasyMock.expect(messageSource.getMessage(
                EasyMock.eq("error.invalid.request"),
                EasyMock.isNull(),
                EasyMock.eq(Locale.ENGLISH))
        ).andReturn("Invalid request data").once();

        EasyMock.replay(messageSource);

        // When
        ApiErrorResponse response = globalExceptionHandler.handleBusinessException(exception, Locale.ENGLISH);

        // Then
        assertEquals(response.getStatus(), "400"); // BAD_REQUEST
        assertEquals(response.getCode(), "error.invalid.request");
        // Minimal approach: We could skip checking response.getMessage() exactly
        // but you can do it if you want: "Invalid request data"

        EasyMock.verify(messageSource);
    }

    @Test
    public void testHandleBusinessExceptionGerman() {
        // Given
        BusinessException exception = new BusinessException(ErrorCode.INVALID_REQUEST);

        // Expecting a German message from the messageSource
        EasyMock.expect(messageSource.getMessage(
                EasyMock.eq("error.invalid.request"),
                EasyMock.isNull(),
                EasyMock.eq(Locale.GERMANY))
        ).andReturn("Die Anfrage konnte aufgrund fehlender oder ungültiger Informationen nicht verarbeitet werden.").once();

        EasyMock.replay(messageSource);

        // When
        ApiErrorResponse response = globalExceptionHandler.handleBusinessException(exception, Locale.GERMANY);

        // Then
        assertEquals(response.getStatus(), "400");
        assertEquals(response.getCode(), "error.invalid.request");

        EasyMock.verify(messageSource);
    }

    // ─────────────────────────────────────────────────────────────────
    // 2. TECHNICAL EXCEPTION
    // ─────────────────────────────────────────────────────────────────

    @Test
    public void testHandleTechnicalExceptionEnglish() {
        // Given
        TechnicalException exception = new TechnicalException(ErrorCode.INTERNAL_SERVER_ERROR);

        EasyMock.expect(messageSource.getMessage(
                EasyMock.eq("error.internal"),
                EasyMock.isNull(),
                EasyMock.eq(Locale.ENGLISH))
        ).andReturn("Internal server error").once();

        EasyMock.replay(messageSource);

        // When
        ApiErrorResponse response = globalExceptionHandler.handleTechnicalException(exception, Locale.ENGLISH);

        // Then
        assertEquals(response.getStatus(), "500");
        assertEquals(response.getCode(), "error.internal");

        EasyMock.verify(messageSource);
    }

    @Test
    public void testHandleTechnicalExceptionGerman() {
        // Given
        TechnicalException exception = new TechnicalException(ErrorCode.INTERNAL_SERVER_ERROR);

        EasyMock.expect(messageSource.getMessage(
                EasyMock.eq("error.internal"),
                EasyMock.isNull(),
                EasyMock.eq(Locale.GERMANY))
        ).andReturn("Es ist ein interner Serverfehler aufgetreten.").once();

        EasyMock.replay(messageSource);

        // When
        ApiErrorResponse response = globalExceptionHandler.handleTechnicalException(exception, Locale.GERMANY);

        // Then
        assertEquals(response.getStatus(), "500");
        assertEquals(response.getCode(), "error.internal");

        EasyMock.verify(messageSource);
    }

    // ─────────────────────────────────────────────────────────────────
    // 3. EXTERNAL SERVICE EXCEPTION
    // ─────────────────────────────────────────────────────────────────

    @Test
    public void testHandleExternalServiceExceptionEnglish() {
        // Given
        ExternalServiceException exception = new ExternalServiceException(ErrorCode.EXTERNAL_SERVICE_FAILURE);

        EasyMock.expect(messageSource.getMessage(
                EasyMock.eq("error.external.service.failure"),
                EasyMock.isNull(),
                EasyMock.eq(Locale.ENGLISH))
        ).andReturn("External service failed").once();

        EasyMock.replay(messageSource);

        // When
        ApiErrorResponse response = globalExceptionHandler.handleExternalServiceException(exception, Locale.ENGLISH);

        // Then
        assertEquals(response.getStatus(), "502");
        assertEquals(response.getCode(), "error.external.service.failure");

        EasyMock.verify(messageSource);
    }

    @Test
    public void testHandleExternalServiceExceptionGerman() {
        // Given
        ExternalServiceException exception = new ExternalServiceException(ErrorCode.EXTERNAL_SERVICE_FAILURE);

        EasyMock.expect(messageSource.getMessage(
                EasyMock.eq("error.external.service.failure"),
                EasyMock.isNull(),
                EasyMock.eq(Locale.GERMANY))
        ).andReturn("Externer Dienstfehler").once();

        EasyMock.replay(messageSource);

        // When
        ApiErrorResponse response = globalExceptionHandler.handleExternalServiceException(exception, Locale.GERMANY);

        // Then
        assertEquals(response.getStatus(), "502");
        assertEquals(response.getCode(), "error.external.service.failure");

        EasyMock.verify(messageSource);
    }

    // ─────────────────────────────────────────────────────────────────
    // 4. GENERIC EXCEPTION
    // ─────────────────────────────────────────────────────────────────

    @Test
    public void testHandleGenericExceptionEnglish() {
        // Given
        Exception exception = new Exception("Some unexpected error");

        // Hier wird "error.internal" verwendet
        EasyMock.expect(messageSource.getMessage(
                EasyMock.eq("error.internal"),
                EasyMock.isNull(),
                EasyMock.eq("An internal server error occurred."),
                EasyMock.eq(Locale.ENGLISH)) // Achtung: GenericException-Aufruf hat defaultMessage
        ).andReturn("An internal server error occurred.").once();

        EasyMock.replay(messageSource);

        // When
        ApiErrorResponse response = globalExceptionHandler.handleGenericException(exception, Locale.ENGLISH);

        // Then
        assertEquals(response.getStatus(), "500");
        assertEquals(response.getCode(), "INTERNAL_SERVER_ERROR");
        // Achtung: Hier wird in handleGenericException -> ErrorCode.INTERNAL_SERVER_ERROR.getCode() benutzt
        // => Der Code == "INTERNAL_SERVER_ERROR", wenn du es so implementiert hast!

        EasyMock.verify(messageSource);
    }

    @Test
    public void testHandleGenericExceptionGerman() {
        // Given
        Exception exception = new Exception("Some unexpected error");

        // Hier haben wir "error.internal" + defaultMessage
        EasyMock.expect(messageSource.getMessage(
                EasyMock.eq("error.internal"),
                EasyMock.isNull(),
                EasyMock.eq("An internal server error occurred."),
                EasyMock.eq(Locale.GERMANY))
        ).andReturn("Ein interner Serverfehler ist aufgetreten.").once();

        EasyMock.replay(messageSource);

        // When
        ApiErrorResponse response = globalExceptionHandler.handleGenericException(exception, Locale.GERMANY);

        // Then
        assertEquals(response.getStatus(), "500");
        assertEquals(response.getCode(), "INTERNAL_SERVER_ERROR");
        // Beachte: code == "INTERNAL_SERVER_ERROR"
        // (getCode() von ErrorCode.INTERNAL_SERVER_ERROR)

        EasyMock.verify(messageSource);
    }
}
