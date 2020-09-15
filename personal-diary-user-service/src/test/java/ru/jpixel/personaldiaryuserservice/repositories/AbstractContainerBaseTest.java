package ru.jpixel.personaldiaryuserservice.repositories;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class AbstractContainerBaseTest {
    protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:12.4")
                .withUsername("test")
                .withPassword("test")
                .waitingFor(Wait.forListeningPort());
        POSTGRE_SQL_CONTAINER.start();
    }
}
