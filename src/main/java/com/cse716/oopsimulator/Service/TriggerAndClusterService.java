package com.cse716.oopsimulator.Service;

import com.cse716.oopsimulator.Dto.StudentDto;
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
public class TriggerAndClusterService {

    private final DataSource dataSource;

    public boolean insertData(Map<String, String> values, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            String query = buildInsertQuery(values, tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
            return false;
        }
        return true;
    }

    private String buildInsertQuery(Map<String, String> values, String tableName) {
        StringBuilder query = new StringBuilder();
        StringBuilder value = new StringBuilder("(");
        query.append("INSERT INTO ").append(tableName).append("(");
        values.keySet().forEach(key -> {
            query.append(key).append(",");
            value.append("'").append(values.get(key)).append("'").append(",");
        });
        query.deleteCharAt(query.length() - 1);
        value.deleteCharAt(value.length() - 1);
        query.append(")");
        value.append(")");
        query.append(" values ").append(value);
        return query.toString();
    }

    public boolean createTrigger(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            String triggerQuery = "CREATE TRIGGER " + tableName + "save_entry " +
                    "BEFORE INSERT " +
                    "ON  " + tableName + " " +
                    "EXECUTE PROCEDURE save_insert_record()";
            PreparedStatement preparedStatement = connection.prepareStatement(triggerQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Map<String, String>> getTableDataByQuery(String tableName) {
        List<Map<String, String>> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String triggerQuery = "SELECT * FROM " + tableName;
            PreparedStatement preparedStatement = connection.prepareStatement(triggerQuery);
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
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
        }
        return results;
    }

    public boolean createCluster(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            String triggerQuery = "CLUSTER" + tableName + " USING " + tableName + "_pkey;";
            PreparedStatement preparedStatement = connection.prepareStatement(triggerQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
            return false;
        }
        return true;
    }
}
