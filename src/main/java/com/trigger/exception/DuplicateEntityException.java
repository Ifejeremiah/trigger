package com.trigger.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }
}
