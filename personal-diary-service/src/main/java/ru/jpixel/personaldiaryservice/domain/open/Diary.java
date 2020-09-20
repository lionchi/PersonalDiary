package ru.jpixel.personaldiaryservice.domain.open;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DIARIES")
@Getter
@Setter
@NoArgsConstructor
public class Diary {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIARY_SEQ")
    @SequenceGenerator(name = "DIARY_SEQ", sequenceName = "DIARY_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Lob
    @Column(name = "KEY", nullable = false)
    private byte[] key;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Page> pages = new ArrayList<>();
}
