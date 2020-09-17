package ru.jpixel.personaldiaryuserservice.domain.secr;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jpixel.personaldiaryuserservice.domain.open.Diary;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS", schema = "SECR")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", schema = "SECR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "PREFIX")
    private String prefix;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES", schema = "SECR", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Diary diary;
}
