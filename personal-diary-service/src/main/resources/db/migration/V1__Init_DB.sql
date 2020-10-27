CREATE SEQUENCE IF NOT EXISTS diary_seq as BIGINT;
CREATE SEQUENCE IF NOT EXISTS page_seq as BIGINT;

CREATE TABLE IF NOT EXISTS diaries
(
    id      bigint not null default nextval('diary_seq'),
    user_id bigint not null,
    key     oid    not null,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS tags
(
    id      bigint      not null,
    name_ru varchar(50) not null,
    name_en varchar(50) not null,
    code    varchar(5)  not null,
    primary key (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS idx_tags on tags (code);

CREATE TABLE IF NOT EXISTS pages
(
    id                bigint    not null default nextval('page_seq'),
    diary_id          bigint    not null,
    content           text      not null,
    tag_id            bigint    not null,
    notification_date date,
    create_date       timestamp not null,
    recording_summary text      not null,
    confidential      boolean   not null default false,
    primary key (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS idx_pages on pages (notification_date, create_date);
ALTER TABLE IF EXISTS pages
    ADD CONSTRAINT fk_page_on_diary FOREIGN KEY (diary_id) REFERENCES diaries (id) ON DELETE CASCADE;
ALTER TABLE IF EXISTS pages
    ADD CONSTRAINT fk_page_on_tag FOREIGN KEY (tag_id) REFERENCES tags (id);

insert into tags (id, name_ru, name_en, code)
VALUES (1, 'Заметка', 'Note', '1');
insert into tags (id, name_ru, name_en, code)
VALUES (2, 'Уведомление', 'Notification', '2');
insert into tags (id, name_ru, name_en, code)
VALUES (3, 'Напоминание', 'Reminder', '3');
insert into tags (id, name_ru, name_en, code)
VALUES (4, 'Закладка', 'Bookmark', '4');

