package com.cse716.oopsimulator.Service;

import com.cse716.oopsimulator.Dto.TriggerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TriggerAndClusterService {

    private final DataSource dataSource;
    private final RASimulatorService raSimulatorService;

    public boolean insertData(Map<String, String> values, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            String query = buildInsertQuery(values, tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println(query);
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

    public boolean createTrigger(TriggerDto triggerDto) {
        try (Connection connection = dataSource.getConnection()) {
            String triggerQuery = "CREATE TRIGGER " + triggerDto.getTriggerName() + " " +
                    "BEFORE INSERT " +
                    "ON  " + triggerDto.getTableName() + " " +
                    "EXECUTE PROCEDURE save_insert_record()";
            PreparedStatement preparedStatement = connection.prepareStatement(triggerQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println(triggerQuery);
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Map<String, String>> getTableDataByQuery(String tableName) {
        try {
            String triggerQuery = "SELECT * FROM " + tableName;
            return raSimulatorService.getQueryResultMapFromQueryString(triggerQuery);
        } catch (SQLException e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new BadResultException(e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR]: RASimulatorService.getResultMapFromPreparedStatement: " + e.getMessage());
//            throw new ServerErrorException(e.getMessage());
        }
        return new ArrayList<>();
    }

    public boolean createCluster(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            String triggerQuery = "CLUSTER " + tableName + " USING " + tableName + "_pkey";
            PreparedStatement preparedStatement = connection.prepareStatement(triggerQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println(triggerQuery);
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
            return false;
        }
        return true;
    }

    public List<String> getTableNames() {
        List<String> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String triggerQuery = "SELECT tablename FROM pg_catalog.pg_tables where tableowner = 'appdb'";
            PreparedStatement preparedStatement = connection.prepareStatement(triggerQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
        }
        return results;
    }

    public Map<String, String> getDbInfo() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT substr(version(), 1, 69)");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Map.of("version", resultSet.getString(1));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
        }
        return new HashMap<>();
    }

}
