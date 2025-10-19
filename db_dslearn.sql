DROP DATABASE IF EXISTS db_dslearn;
CREATE DATABASE db_dslearn;
USE db_dslearn;

CREATE SCHEMA IF NOT EXISTS `db_dslearn`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;
USE `db_dslearn`;

CREATE TABLE `tb_user`
(
    `id`       BIGINT       NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `email`    VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `tb_user_email_unique` UNIQUE (`email`)
) ENGINE = InnoDB;

CREATE TABLE `tb_role`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `authority` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_user_role`
(
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
    CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_course`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(255) NOT NULL,
    `img_uri`      VARCHAR(255),
    `img_gray_uri` VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_offer`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `edition`      VARCHAR(255),
    `start_moment` TIMESTAMP(6) NULL,
    `end_moment`   TIMESTAMP(6) NULL,
    `course_id`    BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_offer_course` FOREIGN KEY (`course_id`) REFERENCES `tb_course` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_resource`
(
    `id`          BIGINT NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(255),
    `description` VARCHAR(255),
    `position`    INT,
    `img_uri`     VARCHAR(255),
    `type`        VARCHAR(32),
    `offer_id`    BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_resource_offer` FOREIGN KEY (`offer_id`) REFERENCES `tb_offer` (`id`),
    CONSTRAINT `chk_resource_type` CHECK (`type` IN ('LESSON_ONLY', 'LESSON_TASK', 'FORUM', 'EXTERNAL_LINK'))
) ENGINE = InnoDB;

CREATE TABLE `tb_section`
(
    `id`              BIGINT NOT NULL AUTO_INCREMENT,
    `title`           VARCHAR(255),
    `description`     VARCHAR(255),
    `position`        INT,
    `img_uri`         VARCHAR(255),
    `resource_id`     BIGINT,
    `prerequisite_id` BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_section_resource` FOREIGN KEY (`resource_id`) REFERENCES `tb_resource` (`id`),
    CONSTRAINT `fk_section_prerequisite` FOREIGN KEY (`prerequisite_id`) REFERENCES `tb_section` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_lesson`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(255),
    `position`   INT,
    `section_id` BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_lesson_section` FOREIGN KEY (`section_id`) REFERENCES `tb_section` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_content`
(
    `id`        BIGINT NOT NULL,
    `text`      TEXT,
    `video_uri` VARCHAR(255),
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_content_lesson` FOREIGN KEY (`id`) REFERENCES `tb_lesson` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_task`
(
    `id`             BIGINT       NOT NULL,
    `description`    VARCHAR(255),
    `question_count` INT,
    `approval_count` INT,
    `weight`         DOUBLE,
    `due_date`       TIMESTAMP(6) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_task_lesson` FOREIGN KEY (`id`) REFERENCES `tb_lesson` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_enrollment`
(
    `user_id`       BIGINT   NOT NULL,
    `offer_id`      BIGINT   NOT NULL,
    `available`     BOOLEAN,
    `only_update`   BOOLEAN,
    `enroll_moment` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `refund_moment` DATETIME NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `offer_id`),
    CONSTRAINT `fk_enrollment_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
    CONSTRAINT `fk_enrollment_offer` FOREIGN KEY (`offer_id`) REFERENCES `tb_offer` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_lessons_done`
(
    `lesson_id` BIGINT NOT NULL,
    `user_id`   BIGINT NOT NULL,
    `offer_id`  BIGINT NOT NULL,
    PRIMARY KEY (`lesson_id`, `user_id`, `offer_id`),
    CONSTRAINT `fk_lessons_done_lesson` FOREIGN KEY (`lesson_id`) REFERENCES `tb_lesson` (`id`),
    CONSTRAINT `fk_lessons_done_enrollment` FOREIGN KEY (`user_id`, `offer_id`) REFERENCES `tb_enrollment` (`user_id`, `offer_id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_notification`
(
    `id`      BIGINT   NOT NULL AUTO_INCREMENT,
    `text`    VARCHAR(255),
    `moment`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `reading` BOOLEAN,
    `route`   VARCHAR(255),
    `user_id` BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_notification_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_topic`
(
    `id`        BIGINT   NOT NULL AUTO_INCREMENT,
    `title`     VARCHAR(255),
    `body`      TEXT,
    `moment`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `author_id` BIGINT,
    `offer_id`  BIGINT,
    `lesson_id` BIGINT,
    `reply_id`  BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_topic_author` FOREIGN KEY (`author_id`) REFERENCES `tb_user` (`id`),
    CONSTRAINT `fk_topic_offer` FOREIGN KEY (`offer_id`) REFERENCES `tb_offer` (`id`),
    CONSTRAINT `fk_topic_lesson` FOREIGN KEY (`lesson_id`) REFERENCES `tb_lesson` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_reply`
(
    `id`        BIGINT   NOT NULL AUTO_INCREMENT,
    `body`      TEXT,
    `moment`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `topic_id`  BIGINT,
    `author_id` BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_reply_topic` FOREIGN KEY (`topic_id`) REFERENCES `tb_topic` (`id`),
    CONSTRAINT `fk_reply_author` FOREIGN KEY (`author_id`) REFERENCES `tb_user` (`id`)
) ENGINE = InnoDB;

ALTER TABLE `tb_topic`
    ADD CONSTRAINT `fk_topic_answer`
        FOREIGN KEY (`reply_id`) REFERENCES `tb_reply` (`id`);

CREATE TABLE `tb_replies_likes`
(
    `reply_id` BIGINT NOT NULL,
    `user_id`  BIGINT NOT NULL,
    PRIMARY KEY (`reply_id`, `user_id`),
    CONSTRAINT `fk_replies_likes_reply` FOREIGN KEY (`reply_id`) REFERENCES `tb_reply` (`id`),
    CONSTRAINT `fk_replies_likes_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_topics_likes`
(
    `topic_id` BIGINT NOT NULL,
    `user_id`  BIGINT NOT NULL,
    PRIMARY KEY (`topic_id`, `user_id`),
    CONSTRAINT `fk_topics_likes_topic` FOREIGN KEY (`topic_id`) REFERENCES `tb_topic` (`id`),
    CONSTRAINT `fk_topics_likes_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tb_deliver`
(
    `id`            BIGINT   NOT NULL AUTO_INCREMENT,
    `uri`           VARCHAR(255),
    `feedback`      VARCHAR(255),
    `correct_count` INT,
    `status`        VARCHAR(32),
    `moment`        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `lesson_id`     BIGINT,
    `user_id`       BIGINT,
    `offer_id`      BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_deliver_lesson` FOREIGN KEY (`lesson_id`) REFERENCES `tb_lesson` (`id`),
    CONSTRAINT `fk_deliver_enrollment` FOREIGN KEY (`user_id`, `offer_id`) REFERENCES `tb_enrollment` (`user_id`, `offer_id`),
    CONSTRAINT `chk_deliver_status` CHECK (`status` IN ('PENDING', 'ACCEPTED', 'REJECTED'))
) ENGINE = InnoDB;
