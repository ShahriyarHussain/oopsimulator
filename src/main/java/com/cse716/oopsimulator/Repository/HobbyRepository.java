package com.cse716.oopsimulator.Repository;

import com.cse716.oopsimulator.Entity.Hobby;
import com.cse716.oopsimulator.Entity.HobbyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, HobbyId> {
}
