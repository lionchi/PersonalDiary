package ru.jpixel.personaldiaryuserservice.domain.secr;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROLES", schema = "SECR")
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
