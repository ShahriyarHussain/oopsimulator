package com.cse716.oopsimulator.Entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class HobbyId implements Serializable {
    private Long id;
    private String hobby;


}
