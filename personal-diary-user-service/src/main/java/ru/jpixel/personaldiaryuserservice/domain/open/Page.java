package ru.jpixel.personaldiaryuserservice.domain.open;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PAGES")
@Getter
@Setter
@NoArgsConstructor
public class Page {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAGE_SEQ")
    @SequenceGenerator(name = "PAGE_SEQ", sequenceName = "PAGE_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DIARY_ID", nullable = false)
    private Diary diary;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "TAG_ID", nullable = false)
    private Tag tag;

    @Column(name = "notification_date")
    private LocalDate notificationDate;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "RECORDING_SUMMARY", nullable = false)
    private String recordingSummary;

    @Column(name = "CONFIDENTIAL", nullable = false)
    private boolean confidential;
}
