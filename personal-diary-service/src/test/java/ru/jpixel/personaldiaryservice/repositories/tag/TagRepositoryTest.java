package ru.jpixel.personaldiaryservice.repositories.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jpixel.personaldiaryservice.domain.open.Tag;
import ru.jpixel.personaldiaryservice.repositories.BaseRepositoryTest;
import ru.jpixel.personaldiaryservice.repositories.open.TagRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Поиск тега по его коду")
    public void findByCodeTest() {
        var tag = new Tag();
        tag.setId(1L);
        tag.setNameRu("Заметка");
        tag.setNameEn("Note");
        tag.setCode("1");
        testEntityManager.persist(tag);

        assertNotNull(tagRepository.findByCode(tag.getCode()));
    }
}
