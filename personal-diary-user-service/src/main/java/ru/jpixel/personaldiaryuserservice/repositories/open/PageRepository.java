package ru.jpixel.personaldiaryuserservice.repositories.open;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jpixel.personaldiaryuserservice.domain.open.Page;

public interface PageRepository extends JpaRepository<Page, Long> {
}
