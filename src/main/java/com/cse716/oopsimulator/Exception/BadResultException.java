package com.cse716.oopsimulator.Exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Request From Client Side")
@AllArgsConstructor
@NoArgsConstructor
public class BadResultException extends RuntimeException{
    public BadResultException(String message) {
        super(message);
    }
}
