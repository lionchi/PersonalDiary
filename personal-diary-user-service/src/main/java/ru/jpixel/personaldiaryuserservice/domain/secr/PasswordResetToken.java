package ru.jpixel.personaldiaryuserservice.domain.secr;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PASSWORD_RESET_TOKENS", schema = "SECR")
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetToken {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PASSWORD_RESET_TOKEN_SEQ", sequenceName = "PASSWORD_RESET_TOKEN_SEQ", schema = "SECR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASSWORD_RESET_TOKEN_SEQ")
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;

    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private LocalDate expiryDate;

    public boolean isExpired() {
        return LocalDate.now().compareTo(expiryDate) > 0;
    }
}
