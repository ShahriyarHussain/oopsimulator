package com.cse716.oopsimulator.Dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class JoinRequestDto {
    private String tableName1;
    private String tableName2;
    private Map<String, String> whereClause;
}
