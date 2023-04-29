package com.cse716.oopsimulator.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JoinRequestDto {
    private String tableName1;
    private String tableName2;
    private List<ConditionDto> whereClause;

    private List<String> selectColumns;

    private String joinType;

    public String getJoinType() {
        if (joinType == null) {
            return null;
        }
        return joinType.toUpperCase();
    }
}
