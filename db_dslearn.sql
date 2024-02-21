DROP DATABASE IF EXISTS db_dslearn;
CREATE DATABASE db_dslearn;
USE db_dslearn;

CREATE TABLE IF NOT EXISTS notification
(
    id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    moment  TIMESTAMP       NULL,
    reading BIT             NULL,
    route   VARCHAR(255)    NULL,
    text    VARCHAR(255)    NULL,
    user_id BIGINT UNSIGNED NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (user_id) REFERENCES tb_user (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS offer
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    edition      VARCHAR(255)    NULL,
    end_moment   TIMESTAMP       NULL,
    start_moment TIMESTAMP       NULL,
    course_id    BIGINT UNSIGNED NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (course_id) REFERENCES tb_course (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_content
(
    text      VARCHAR(255)    NULL,
    video_uri VARCHAR(255)    NULL,
    id        BIGINT UNSIGNED NULL,

    PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_course
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    img_gray_uri VARCHAR(255),
    img_uri      VARCHAR(255),
    name         VARCHAR(255),

    PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_deliver
(
    id            BIGINT UNSIGNED                        NOT NULL AUTO_INCREMENT,
    correct_count integer                                NULL,
    feedback      VARCHAR(255)                           NULL,
    moment        TIMESTAMP                              NULL,
    status        ENUM ('PENDING','ACCEPTED','REJECTED') NOT NULL,
    uri           VARCHAR(255)                           NULL,
    user_id       BIGINT UNSIGNED                        NULL,
    offer_id      BIGINT UNSIGNED                        NULL,
    lesson_id     BIGINT UNSIGNED                        NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id, offer_id) REFERENCES tb_enrollment (offer_id, user_id),
    FOREIGN KEY (lesson_id) REFERENCES tb_lesson (id)

) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_enrollment
(
    available     BIT             NOT NULL,
    only_update   BIT             NOT NULL,
    enroll_moment TIMESTAMP       NULL,
    refund_moment TIMESTAMP       NULL,

    offer_id      BIGINT UNSIGNED NOT NULL,
    user_id       BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (offer_id, user_id),

    FOREIGN KEY (offer_id) REFERENCES offer (id),
    FOREIGN KEY (user_id) REFERENCES tb_user (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_lesson
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    position   INTEGER         NULL,
    title      VARCHAR(255)    NULL,
    section_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (section_id) REFERENCES tb_section (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_reply
(
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    body      TEXT            NULL,
    moment    TIMESTAMP       NULL,
    author_id BIGINT UNSIGNED NOT NULL,
    topic_id  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (author_id) REFERENCES tb_user (id),
    FOREIGN KEY (topic_id) REFERENCES tb_topic (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_resource
(
    id         BIGINT UNSIGNED AUTO_INCREMENT,
    img_uri    VARCHAR(255)    NULL,
    link       VARCHAR(255)    NULL,
    position   INTEGER         NULL NULL,
    title      VARCHAR(255)    NULL,
    type       ENUM ('LESSON_ONLY','LESSON_TASK','FORUM','EXTERNAL_LINK'),
    offer_id   BIGINT UNSIGNED NOT NULL,
    section_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (offer_id) REFERENCES offer (id),
    FOREIGN KEY (section_id) REFERENCES tb_section (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_role
(
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    authority VARCHAR(255),

    PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_section
(
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    description     VARCHAR(255),
    img_uri         VARCHAR(255),
    position        integer,
    title           VARCHAR(255),
    prerequisite_id BIGINT,
    resource_id     BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (prerequisite_id) REFERENCES tb_section (id),
    FOREIGN KEY (resource_id) REFERENCES tb_resource (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_task
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    approval_count INTEGER   NULL,
    description    VARCHAR(255),
    due_date       TIMESTAMP NULL,
    question_count INTEGER   NULL,
    weight         FLOAT(53) NULL,

    PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_topic
(
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    body      TEXT            NULL,
    moment    TIMESTAMP       NULL,
    title     VARCHAR(255)    NULL,
    reply_id  BIGINT UNSIGNED NOT NULL,
    author_id BIGINT UNSIGNED NOT NULL,
    lesson_id BIGINT UNSIGNED NOT NULL,
    offer_id  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (reply_id) REFERENCES tb_reply (id),
    FOREIGN KEY (author_id) REFERENCES tb_user (id),
    FOREIGN KEY (lesson_id) REFERENCES tb_lesson (id),
    FOREIGN KEY (offer_id) REFERENCES offer (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_user
(
    id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255),
    name     VARCHAR(255),
    password VARCHAR(255),

    UNIQUE (email),

    PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_lessons_done
(
    lesson_id BIGINT UNSIGNED NOT NULL,
    user_id   BIGINT UNSIGNED NOT NULL,
    offer_id  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (lesson_id, user_id, offer_id),
    FOREIGN KEY (lesson_id) REFERENCES tb_lesson (id),

    FOREIGN KEY (user_id, offer_id) REFERENCES tb_enrollment (user_id, offer_id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_replies_likes
(
    reply_id BIGINT UNSIGNED NOT NULL,
    user_id  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (reply_id, user_id),

    FOREIGN KEY (reply_id) REFERENCES tb_reply (id),
    FOREIGN KEY (user_id) REFERENCES tb_user (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_resource_seq
(
    next_val BIGINT UNSIGNED NOT NULL
) engine = InnoDB;
insert into tb_resource_seq
values (1);

CREATE TABLE IF NOT EXISTS tb_topics_likes
(
    topic_id BIGINT UNSIGNED NOT NULL,
    user_id  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (topic_id, user_id),

    FOREIGN KEY (topic_id) REFERENCES tb_topic (id),
    FOREIGN KEY (user_id) REFERENCES tb_user (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS tb_user_role
(
    user_id BIGINT UNSIGNED NOT NULL,
    role_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (user_id, role_id),

    FOREIGN KEY (user_id) REFERENCES tb_user (id),
    FOREIGN KEY (role_id) REFERENCES tb_role (id)
) engine = InnoDB;