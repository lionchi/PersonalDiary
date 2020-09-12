package ru.jpixel.personaldiaryuserservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryuserservice.domain.PasswordResetToken;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    boolean existsByUserEmail(String email);
}
