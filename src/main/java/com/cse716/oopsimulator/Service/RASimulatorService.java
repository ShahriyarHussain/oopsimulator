package com.cse716.oopsimulator.Service;

import com.cse716.oopsimulator.Dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RASimulatorService {

    private final DataSource dataSource;

    public List<Map<String, String>> getSelectionResult(String tableName, List<ConditionDto> whereClause) {
        try {
            return getQueryResultMapFromQueryString(getSelectionQuery(tableName, whereClause));
        } catch (SQLException e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new BadResultException(e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new ServerErrorException(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Map<String, String>> getProjectionResult(String tableName, List<String> projectionAttributes) {
        try {
            return getQueryResultMapFromQueryString(projectionQuery(tableName, projectionAttributes));
        } catch (SQLException e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new BadResultException(e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new ServerErrorException(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Map<String, String>> getOperationResult(OperationDto operationDto) {
        try {
            return getQueryResultMapFromQueryString(getOperationQuery(operationDto));
        } catch (SQLException e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new BadResultException(e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new ServerErrorException(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Map<String, String>> getJoinOperationResult(JoinRequestDto joinRequestDto) {
        try {
            return getQueryResultMapFromQueryString(makeJoinQuery(joinRequestDto));
        } catch (SQLException e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new BadResultException(e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new ServerErrorException(e.getMessage());
        }
        return new ArrayList<>();
    }

    //TODO: requires refactoring: Method actions hard to comprehend
    public ResponseDto alterColumnName(RenameDto renameDto) throws SQLException {
        StringBuilder query = new StringBuilder();
        PreparedStatement preparedStatement = null;
        query.append("ALTER TABLE ").append(renameDto.getTableName()).append(" RENAME ")
                .append(renameDto.getColumnName()).append(" TO ").append(renameDto.getNewColumnName());
        System.out.println(query);
        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("error!");
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return new ResponseDto("Success!");
    }

    //TODO: Requires Refactoring To Meet Clean Coding Method Size
    private List<Map<String, String>> getQueryResultMapFromQueryString(String query) throws Exception {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        List<Map<String, String>> results = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, String> rowValue = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowValue.put(resultSetMetaData.getColumnLabel(i), resultSet.getString(i));
            }
            results.add(rowValue);
        }
        resultSet.close();
        preparedStatement.close();
        return results;
    }

    private String getOperationQuery(OperationDto operationDto) {
        return "(SELECT * FROM " + operationDto.getTableName1() + ") " +
                " " + operationDto.getOperation().toUpperCase() + " " +
                "(SELECT * FROM " + operationDto.getTableName2() + ") ";
    }

    private String projectionQuery(String tableName, List<String> projectionAttributes) {
        StringBuilder query = new StringBuilder("SELECT ");
        if (projectionAttributes != null && !projectionAttributes.isEmpty()) {
            projectionAttributes.forEach(attribute -> query.append(attribute).append(","));
            deleteLastChar(query);
        } else {
            query.append("*");
        }
        query.append(" FROM ").append(tableName);

        System.out.println(query);
        return query.toString();
    }

    private String getSelectionQuery(String tableName, List<ConditionDto> whereClause) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(tableName).append(" ");
        if (whereClause != null && !whereClause.isEmpty() && whereClause.get(0) != null) {
            query.append("WHERE ");
            whereClause.forEach(conditionDto -> query.append(conditionDto.getAttribute())
                    .append(conditionDto.getComparer())
                    .append("'").append(conditionDto.getValue()).append("'"));
        }
        return query.toString();
    }

    //TODO: Requires Refactoring To Meet Clean Coding Method Size
    private String makeJoinQuery(JoinRequestDto joinRequestDto) {
        StringBuilder query = new StringBuilder();
        if (joinRequestDto.getSelectColumns() == null || joinRequestDto.getSelectColumns().isEmpty()) {
            query.append("SELECT * FROM ");
        } else {
            query.append("SELECT ");
            joinRequestDto.getSelectColumns().forEach(column ->
                    query.append("a.").append(column).append(",").append("b.").append(column).append(" ,"));
            deleteLastChar(query);
            query.append(" FROM ");
        }
        if (joinRequestDto.getJoinType() == null || joinRequestDto.getJoinType().isEmpty()) {
            query.append(joinRequestDto.getTableName1()).append(" a , ").append(joinRequestDto.getTableName2()).append(" b");
        } else {
            query.append(joinRequestDto.getTableName1()).append(" a ")
                    .append(joinRequestDto.getJoinType()).append(" ")
                    .append(joinRequestDto.getTableName2()).append(" b ");
        }

        if (joinRequestDto.getWhereClause() != null && !joinRequestDto.getWhereClause().isEmpty()) {
            if (joinRequestDto.getJoinType() == null || joinRequestDto.getJoinType().isEmpty()) {
                query.append(" WHERE ");
            } else {
                query.append(" ON ");
            }
            joinRequestDto.getWhereClause().forEach(filter ->
                    query.append("a.").append(filter.getAttribute()).append(" ")
                            .append(filter.getComparer()).append(" b.").append(filter.getAttribute()));
        }
        System.out.println(query);
        return query.toString();
    }

    private void deleteLastChar(StringBuilder query) {
        query.deleteCharAt(query.length() - 1);
    }






}
