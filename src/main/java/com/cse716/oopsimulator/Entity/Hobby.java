package com.cse716.oopsimulator.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HOBBIES")
@Getter
@Setter
public class Hobby {

    @EmbeddedId
    private HobbyId hobbyId;

}
