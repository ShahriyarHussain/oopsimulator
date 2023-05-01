package com.cse716.oopsimulator.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No Data Found For Query")
public class NoDataException extends RuntimeException{
    public NoDataException(String message) {
        super(message);
    }
}
