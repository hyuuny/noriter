package com.hyuuny.noriter.support.common.abstraction;

import com.hyuuny.noriter.support.common.code.NoriterResponseCode;
import com.hyuuny.noriter.support.common.dto.NoriterResponseDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    protected <T> ResponseEntity<NoriterResponseDto<T>> noContent() {
        return ResponseEntity.noContent().build();
    }

    protected <T> ResponseEntity<NoriterResponseDto<T>> created(T data) {
        NoriterResponseDto<T> response = new NoriterResponseDto<>(
                NoriterResponseCode.CREATED,
                null,
                data,
                null,
                null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    protected <T> ResponseEntity<NoriterResponseDto<T>> ok(@NotNull T data) {
        NoriterResponseDto<T> response = new NoriterResponseDto<>(data);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<NoriterResponseDto<T>> error(@NotNull HttpStatus status, @NotNull T data) {
        NoriterResponseDto<T> response = new NoriterResponseDto<>(data);
        return ResponseEntity.status(status).body(response);
    }

    protected <T> ResponseEntity<NoriterResponseDto<T>> okAdditional(
            @NotNull T data,
            @NotNull NoriterResponseCode responseCode,
            String message) {
        NoriterResponseDto<T> response = new NoriterResponseDto<>(
                responseCode,
                message,
                data,
                null,
                null);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<NoriterResponseDto<T>> ok(@NotNull T data, Long totalPage, Long totalCount) {
        NoriterResponseDto<T> response = new NoriterResponseDto<>(data, totalPage, totalCount);
        return ResponseEntity.ok(response);
    }

}
