package com.hyuuny.noriter.support.config.web.exceptions;

import com.hyuuny.noriter.support.common.code.NoriterResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class NoriterException extends ResponseStatusException {

    private Integer code;
    private String message;

    public NoriterException(Exception e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.code = NoriterResponseCode.UNKNOWN.getCode();
        this.message = e.getMessage();
    }

    public NoriterException(HttpStatus status) {
        super(status);
        NoriterResponseCode responseCode = NoriterResponseCode.findByHttpStatus(status);
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public NoriterException(HttpStatus status, String message) {
        super(status);
        NoriterResponseCode responseCode = NoriterResponseCode.findByHttpStatus(status);
        this.code = responseCode.getCode();
        this.message = message;
    }

    public NoriterException(NoriterResponseCode responseCode) {
        super(NoriterResponseCode.findByNoriterResponseCode(responseCode));
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public NoriterException(NoriterResponseCode responseCode, String errorMessage) {
        super(NoriterResponseCode.findByNoriterResponseCode(responseCode));
        this.code = responseCode.getCode();
        this.message = errorMessage != null ? errorMessage : responseCode.getMessage();
    }

    public NoriterException(String errorMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.code = NoriterResponseCode.UNKNOWN.getCode();
        this.message = errorMessage != null ? errorMessage : NoriterResponseCode.UNKNOWN.getMessage();
    }

}
