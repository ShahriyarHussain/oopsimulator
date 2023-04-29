package com.cse716.oopsimulator.Service;

import com.cse716.oopsimulator.Dto.*;
import com.cse716.oopsimulator.Entity.Student;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RASimulatorService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final DataSource dataSource;

    public List<StudentDto> getSelectionResult(String tableName, List<ConditionDto> whereClause) {
        String query = createSelectionQuery(tableName, whereClause);
        return entityManager.createQuery(query, Student.class).getResultList()
                .stream().map(StudentDto::new).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getProjectionResult(String tableName, List<String> projectionAttributes) {
        String query = projectionQuery(tableName, projectionAttributes);
        List<Object[]> results = entityManager.createQuery(query).getResultList();
        return getAsObjectList(projectionAttributes, results);
    }

    public List<Map<String, Object>> getOperationResult(OperationDto operationDto) {
        String query = getOperationQuery(operationDto);
        if (operationDto.getColumns() != null && operationDto.getColumns().size() == 1) {
            return getObjectList(entityManager.createNativeQuery(query).getResultList(), operationDto.getColumns().get(0));
        }
        List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
        return getAsObjectList(operationDto.getColumns(), results);
    }

    private List<Map<String, Object>> getObjectList(List<?> results, String columnName) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        results.forEach(result -> resultList.add(Map.of(columnName, result.toString())));
        return resultList;
    }

    private String getOperationQuery(OperationDto operationDto) {
        StringBuilder query = new StringBuilder();
        List<String> columns = operationDto.getColumns();
        query.append("(SELECT ");
        if (columns != null && columns.size() > 0) {
            columns.forEach(col -> query.append(col).append(","));
            deleteLastChar(query);
        } else {
            query.append("* ");
        }
        String projectionPart = query.toString();
        query.append(" FROM ").append(operationDto.getTableName1()).append(") ")
                .append(" ").append(operationDto.getOperation()).append(" ")
                .append(projectionPart).append(" FROM ").append(operationDto.getTableName2()).append(") ");
        return query.toString();
    }

    private void deleteLastChar(StringBuilder query) {
        query.deleteCharAt(query.length() - 1);
    }

    private List<Map<String, Object>> getAsObjectList(List<String> columns, List<Object[]> results) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        results.forEach(result -> {
            Map<String, Object> objectMap = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                result[i] = result[i].toString();
                objectMap.put(columns.get(i), result[i].toString());
            }
            resultList.add(objectMap);
        });
        return resultList;
    }

    private String projectionQuery(String tableName, List<String> projectionAttributes) {
        StringBuilder query = new StringBuilder("select ");
        if (projectionAttributes != null && !projectionAttributes.isEmpty()) {
            projectionAttributes.forEach(attribute -> query.append("t.").append(attribute).append(","));
            deleteLastChar(query);
        } else {
            query.append("t");
        }
        query.append(" from ").append(tableName).append(" t");

        System.out.println(query);
        return query.toString();
    }

    private String createSelectionQuery(String tableName, List<ConditionDto> whereClause) {
        StringBuilder query = new StringBuilder("select t from ").append(tableName).append(" t ");
        if (whereClause != null && !whereClause.isEmpty()) {
            query.append("WHERE ");
            whereClause.forEach(conditionDto -> query.append(conditionDto.getAttribute())
                    .append(conditionDto.getComparer()).append(conditionDto.getValue()));
        }
        System.out.println(query);
        return query.toString();
    }


    public ResponseDto alterColumnName(RenameDto renameDto) throws SQLException {
        StringBuilder query = new StringBuilder();
        PreparedStatement preparedStatement = null;
        query.append("ALTER TABLE ").append(renameDto.getTableName()).append(" RENAME ")
                .append(renameDto.getColumnName()).append(" TO ").append(renameDto.getNewColumnName());
        System.out.println(query);
        try(Connection connection = dataSource.getConnection()) {
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

    public List<Object[]> getCrossProductResult(JoinRequestDto joinRequestDto) {

    }

    private String makeJoinQuery(JoinRequestDto joinRequestDto) {
        StringBuilder query = new StringBuilder();
        query.append()
    }



}
