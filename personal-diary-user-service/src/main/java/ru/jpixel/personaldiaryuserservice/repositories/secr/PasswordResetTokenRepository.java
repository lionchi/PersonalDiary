package ru.jpixel.personaldiaryuserservice.repositories.secr;

import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryuserservice.domain.secr.PasswordResetToken;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUserEmail(String email);
}
