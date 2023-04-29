package com.cse716.oopsimulator.Dto;

import com.cse716.oopsimulator.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class StudentDto {

    public StudentDto(Student student) {
        id = student.getId();
        name = student.getName();
        age = student.getAge();
    }

    private Long id;
    private String name;
    private Integer age;
}
