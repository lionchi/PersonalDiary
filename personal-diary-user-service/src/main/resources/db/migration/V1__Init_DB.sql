CREATE SCHEMA IF NOT EXISTS secr;

CREATE SEQUENCE IF NOT EXISTS secr.user_seq as BIGINT;
CREATE SEQUENCE IF NOT EXISTS secr.password_reset_token_seq as BIGINT;

CREATE TABLE IF NOT EXISTS secr.users
(
    id       bigint       not null default nextval('secr.user_seq'),
    name     varchar(255) not null,
    email    varchar(255) not null,
    login    varchar(25)  not null,
    password text         not null,
    prefix   varchar(5),
    phone    varchar(12),
    birthday date,
    primary key (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS idx_users on secr.users (email, login);

CREATE TABLE IF NOT EXISTS secr.roles
(
    id   bigint      not null,
    name varchar(15) not null,
    primary key (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS idx_roles on secr.roles (name);

CREATE TABLE IF NOT EXISTS secr.user_roles
(
    user_id bigint not null,
    role_id bigint not null
);
ALTER TABLE IF EXISTS secr.user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES secr.users (id) ON DELETE CASCADE;
ALTER TABLE IF EXISTS secr.user_roles
    ADD CONSTRAINT fk_user_roles_on_role FOREIGN KEY (role_id) REFERENCES secr.roles (id) on DELETE CASCADE;

CREATE TABLE IF NOT EXISTS secr.password_reset_tokens
(
    id          bigint not null default nextval('secr.password_reset_token_seq'),
    token       text   not null,
    user_id     bigint not null,
    expiry_date date   not null,
    primary key (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS idx_password_reset_tokens on secr.password_reset_tokens (token);
ALTER TABLE IF EXISTS secr.password_reset_tokens
    ADD CONSTRAINT fk_password_reset_token_on_user FOREIGN KEY (user_id) REFERENCES secr.users (id) ON DELETE CASCADE;

insert into secr.roles (id, name)
VALUES (1, 'ADMIN');
insert into secr.roles (id, name)
VALUES (2, 'USER');
insert into secr.roles (id, name)
VALUES (3, 'MODERATOR');

