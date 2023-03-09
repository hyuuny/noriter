package com.hyuuny.noriter.support.config.web.exceptions.handler;

import com.hyuuny.noriter.support.common.code.NoriterResponseCode;
import com.hyuuny.noriter.support.common.dto.NoriterResponseDto;
import com.hyuuny.noriter.support.config.web.exceptions.NoriterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class NoriterExceptionHandler {

    @ExceptionHandler(NoriterException.class)
    public NoriterResponseDto handleException(
            NoriterException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        log.error(exception.getMessage());
        Throwable cause = exception.getCause();
        if (!isEmpty(cause)) {
            printStackTrace(cause);
        } else {
            printStackTrace(exception);
        }
        Integer exceptionCode = exception.getCode() != null ? exception.getCode() : NoriterResponseCode.UNKNOWN.getCode();
        response.setStatus(exceptionCode / 100);
        return new NoriterResponseDto<>(exception.getCode(), exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public NoriterResponseDto handleException(
            Exception exception, HttpServletRequest request, HttpServletResponse response) {
        log.error(exception.getMessage());
        Throwable cause = exception.getCause();
        if (cause != null) {
            printStackTrace(cause);
        } else {
            printStackTrace(exception);
        }
        int statusCode = NoriterResponseCode.UNKNOWN.getCode();
        int statusCodeValue = HttpStatus.INTERNAL_SERVER_ERROR.value();
        if (cause instanceof ConstraintViolationException) {
            statusCodeValue = HttpStatus.CONFLICT.value();
            statusCode = NoriterResponseCode.CONFLICT.getCode();
        }
        response.setStatus(statusCodeValue);
        return new NoriterResponseDto(statusCode, exception.getMessage(), request.getRequestURI());
    }

    private void printStackTrace(Throwable cause) {
        StackTraceElement[] stackTraces = cause.getStackTrace();
        for (StackTraceElement element : stackTraces) {
            String className = element.getClassName();
            String methodName = element.getMethodName();
            int lineNumber = element.getLineNumber();
        }
        Throwable innerCause = cause.getCause();
        if (!isEmpty(innerCause)) {
            printStackTrace(innerCause);
        }

    }

}
