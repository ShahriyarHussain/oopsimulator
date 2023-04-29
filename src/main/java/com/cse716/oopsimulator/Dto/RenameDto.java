package com.cse716.oopsimulator.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RenameDto {

    private String tableName;

    private String columnName;

    private String newColumnName;
}
