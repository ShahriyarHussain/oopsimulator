package com.cse716.oopsimulator.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Student {

    @Id
    private Long id;

    private String name;

    private Integer age;
}
