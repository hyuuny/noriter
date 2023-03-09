package com.hyuuny.noriter.support.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hyuuny.noriter.support.common.code.NoriterResponseCode;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@JsonInclude(Include.NON_NULL)
public class NoriterResponseDto<T> implements Serializable {

    private Integer code;
    private String message;
    private Long totalPage;
    private Long totalCount;
    private String path;
    private T data;

    // For error
    public NoriterResponseDto(Integer code, String message, String path) {
        this.code = code;
        this.message = message;
        this.path = path;
    }

    // For success and equivalent
    public NoriterResponseDto(@NonNull NoriterResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public NoriterResponseDto(@NonNull HttpStatus status) {
        this.code = status.value();
        this.message = status.name();
    }

    public NoriterResponseDto(@NonNull T data) {
        this.code = NoriterResponseCode.SUCCESS.getCode();
        this.message = NoriterResponseCode.SUCCESS.getMessage();
        this.data = data;
    }

    public NoriterResponseDto(@NonNull T data, Long totalPage, Long totalCount) {
        this(NoriterResponseCode.SUCCESS, null, data, totalPage, totalCount);
    }

    public NoriterResponseDto(@NonNull NoriterResponseCode responseCode,
                              String message,
                              @NonNull T data,
                              Long totalPage,
                              Long totalCount) {
        this.code = responseCode.getCode();
        this.message = message != null ? message : responseCode.getMessage();
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.data = data;
    }

}