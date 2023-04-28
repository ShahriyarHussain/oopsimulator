package com.cse716.oopsimulator.Service;

import com.cse716.oopsimulator.Dto.ConditionDto;
import com.cse716.oopsimulator.Dto.StudentDto;
import com.cse716.oopsimulator.Entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RASimulatorService {

    @PersistenceContext
    EntityManager entityManager;

    public List<StudentDto> getSelectionResult(String tableName, List<ConditionDto> whereClause) {
        String query = createSelectionQuery(tableName, whereClause);
        return entityManager.createQuery(query, Student.class).getResultList()
                .stream().map(StudentDto::new).collect(Collectors.toList());
    }

    private String createSelectionQuery(String tableName, List<ConditionDto> whereClause) {
        StringBuilder query = new StringBuilder("select t from ").append(tableName).append(" t ");
        if (whereClause != null && !whereClause.isEmpty()) {
            query.append("WHERE ");
            whereClause.forEach(conditionDto -> query
                    .append(conditionDto.getAttribute())
                    .append(conditionDto.getComparer())
                    .append(conditionDto.getValue()));
        }
        System.out.println(query);
        return query.toString();
    }
}
