package ru.jpixel.personaldiaryservice.repositories.open;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jpixel.personaldiaryservice.domain.open.Page;

public interface PageRepository extends JpaRepository<Page, Long> {
}
