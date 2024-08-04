package com.mt.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends MtCoreException {
    public NotFoundException(Class<?> clazz, Object param) {
        super(HttpStatus.NOT_FOUND, "entity of the class %s was not found in database by %s".formatted(clazz.getSimpleName(), param));
    }
}
