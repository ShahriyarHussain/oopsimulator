package com.cse716.oopsimulator.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OperationDto {
    private String operation;
    private String tableName1;
    private String tableName2;
    private List<String> columns;

    public String getOperation() {
        return this.operation.toUpperCase();
    }
}
