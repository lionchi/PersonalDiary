package ru.jpixel.personaldiaryuserservice.domain.open;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.jpixel.personaldiaryuserservice.domain.Codable;
import ru.jpixel.personaldiaryuserservice.domain.CodableEntity;
import ru.jpixel.personaldiaryuserservice.domain.Directory;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TAGS")
public class Tag extends Directory implements CodableEntity<String, Tag.CodeEnum> {

    @Transient
    @Getter
    private CodeEnum codeEnum;

    public void setCode(String code) {
        super.setCode(code);
        this.codeEnum = parseCode(CodeEnum.class, code);
    }

    @RequiredArgsConstructor
    @Getter
    public enum CodeEnum implements Codable<String> {
        /**
         * Заметка
         */
        NOTE("1"),
        /**
         * Уведомление
         */
        NOTIFICATION("2"),
        /**
         * Напоминание
         */
        REMINDER("3"),
        /**
         * Закладка
         */
        BOOKMARK("4");

        private final String code;
    }
}
