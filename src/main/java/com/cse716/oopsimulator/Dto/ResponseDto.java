package com.cse716.oopsimulator.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto {
    public ResponseDto(String message) {
        responseStatus = message;
    }
    private String responseStatus;
}
