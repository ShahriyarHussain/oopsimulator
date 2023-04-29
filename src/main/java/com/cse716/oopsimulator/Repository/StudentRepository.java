package com.cse716.oopsimulator.Repository;

import com.cse716.oopsimulator.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
