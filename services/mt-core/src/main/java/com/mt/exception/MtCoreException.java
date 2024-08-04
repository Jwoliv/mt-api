package com.mt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public abstract class MtCoreException extends RuntimeException {
    private final HttpStatus status;
    private final String msg;
    private final LocalDateTime timestamp;

    public MtCoreException(final HttpStatus status, final String msg) {
        this.status = status;
        this.msg = msg;
        this.timestamp = LocalDateTime.now();
    }
}
