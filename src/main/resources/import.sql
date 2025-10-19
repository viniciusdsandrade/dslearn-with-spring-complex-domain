USE `db_dslearn`;

INSERT INTO `tb_role` (`id`, `authority`)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_INSTRUCTOR'),
       (3, 'ROLE_STUDENT'),
       (4, 'ROLE_MODERATOR'),
       (5, 'ROLE_SUPPORT');

INSERT INTO `tb_user` (`id`, `name`, `email`, `password`)
VALUES (1, 'Alice Silva', 'alice@example.com', '$2a$10$hashA'),
       (2, 'Bruno Souza', 'bruno@example.com', '$2a$10$hashB'),
       (3, 'Carla Pereira', 'carla@example.com', '$2a$10$hashC'),
       (4, 'Diego Ramos', 'diego@example.com', '$2a$10$hashD'),
       (5, 'Eva Lima', 'eva@example.com', '$2a$10$hashE'),
       (6, 'Felipe Alves', 'felipe@example.com', '$2a$10$hashF');

INSERT INTO `tb_user_role` (`user_id`, `role_id`)
VALUES (1, 1),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 4),
       (5, 3),
       (6, 5),
       (6, 3);

INSERT INTO `tb_course` (`id`, `name`, `img_uri`, `img_gray_uri`)
VALUES (1, 'Java Fundamentals', '/img/java.png', '/img/java_gray.png'),
       (2, 'Spring Boot 3', '/img/spring.png', '/img/spring_gray.png'),
       (3, 'Databases with MySQL', '/img/mysql.png', '/img/mysql_gray.png'),
       (4, 'RESTful APIs', '/img/rest.png', '/img/rest_gray.png'),
       (5, 'Docker & Kubernetes', '/img/containers.png', '/img/containers_gray.png');

INSERT INTO `tb_offer` (`id`, `edition`, `start_moment`, `end_moment`, `course_id`)
VALUES (1, '2025.1', '2025-01-10 09:00:00', '2025-03-30 18:00:00', 1),
       (2, '2025.2', '2025-04-10 09:00:00', '2025-06-30 18:00:00', 1),
       (3, '2025.1', '2025-02-05 09:00:00', '2025-04-30 18:00:00', 2),
       (4, '2025.1', '2025-03-01 09:00:00', '2025-05-31 18:00:00', 3),
       (5, '2025.1', '2025-03-15 09:00:00', '2025-06-15 18:00:00', 4);

INSERT INTO `tb_resource` (`id`, `title`, `description`, `position`, `img_uri`, `type`, `offer_id`)
VALUES (1, 'Intro Module', 'Welcome and setup', 1, NULL, 'LESSON_ONLY', 1),
       (2, 'Assignments', 'Hands-on tasks', 2, NULL, 'LESSON_TASK', 1),
       (3, 'Forum', 'Discussion space', 3, NULL, 'FORUM', 1),
       (4, 'External Docs', 'Reference links', 4, NULL, 'EXTERNAL_LINK', 2),
       (5, 'Spring Basics', 'Core concepts', 1, NULL, 'LESSON_ONLY', 3);

INSERT INTO `tb_section` (`id`, `title`, `description`, `position`, `img_uri`, `resource_id`, `prerequisite_id`)
VALUES (1, 'Getting Started', 'Installing JDK', 1, NULL, 1, NULL),
       (2, 'Java Syntax', 'Variables and control flow', 2, NULL, 1, 1),
       (3, 'OOP', 'Classes and objects', 3, NULL, 1, 2),
       (4, 'Build Tools', 'Maven & Gradle', 4, NULL, 4, NULL),
       (5, 'Spring Intro', 'IoC and Beans', 1, NULL, 5, NULL);

INSERT INTO `tb_lesson` (`id`, `title`, `position`, `section_id`)
VALUES (1, 'Welcome', 1, 1),
       (2, 'Install JDK', 2, 1),
       (3, 'Control Flow', 1, 2),
       (4, 'Collections Task', 2, 2),
       (5, 'OOP Basics', 1, 3),
       (6, 'Spring Context', 1, 5);

INSERT INTO `tb_content` (`id`, `text`, `video_uri`)
VALUES (1, 'Course overview and expectations', 'https://videos.example.com/intro'),
       (2, 'JDK install walkthrough', 'https://videos.example.com/jdk'),
       (5, 'OOP lecture', 'https://videos.example.com/oop');

INSERT INTO `tb_task` (`id`, `description`, `question_count`, `approval_count`, `weight`, `due_date`)
VALUES (3, 'Control flow exercises', 10, 7, 1.0, '2025-02-28 23:59:59'),
       (4, 'Collections coding task', 12, 8, 1.5, '2025-03-10 23:59:59'),
       (6, 'Spring bean wiring task', 8, 6, 1.2, '2025-04-25 23:59:59');

-- ===== Matrículas (PK composta user_id+offer_id) =====
INSERT INTO `tb_enrollment` (`user_id`, `offer_id`, `available`, `only_update`, `enroll_moment`, `refund_moment`)
VALUES (1, 1, 1, 0, '2025-01-09 10:00:00', NULL),
       (2, 1, 1, 0, '2025-01-12 09:00:00', NULL),
       (3, 3, 1, 0, '2025-02-06 09:15:00', NULL),
       (4, 4, 1, 0, '2025-03-02 10:20:00', NULL),
       (5, 2, 1, 0, '2025-04-12 11:45:00', NULL);

INSERT INTO `tb_lessons_done` (`lesson_id`, `user_id`, `offer_id`)
VALUES (1, 1, 1),
       (2, 1, 1),
       (3, 2, 1),
       (4, 2, 1),
       (5, 3, 3);

INSERT INTO `tb_notification` (`id`, `text`, `moment`, `reading`, `route`, `user_id`)
VALUES (1, 'Welcome to the platform!', '2025-01-05 10:00:00', 0, '/home', 1),
       (2, 'Your assignment is due soon', '2025-02-20 08:00:00', 0, '/tasks/4', 2),
       (3, 'New forum post in Java', '2025-02-22 15:30:00', 1, '/forum/1', 3),
       (4, 'Offer 2025.2 opened', '2025-04-01 09:00:00', 0, '/offers/2', 1),
       (5, 'Password changed', '2025-03-12 11:11:00', 1, '/settings', 4);

INSERT INTO `tb_topic` (`id`, `title`, `body`, `moment`, `author_id`, `offer_id`, `lesson_id`, `reply_id`)
VALUES (1, 'How to configure PATH?', 'Having trouble with PATH on Linux.', '2025-01-11 12:00:00', 1, 1, 2, NULL),
       (2, 'When to use List vs Set?', 'Collections best practices.', '2025-01-20 18:30:00', 2, 1, 4, NULL),
       (3, 'OOP vs FP', 'Pros and cons in Java.', '2025-02-15 14:10:00', 3, 1, 5, NULL),
       (4, 'Spring Beans Scope', 'Prototype vs singleton?', '2025-03-20 17:40:00', 4, 3, 6, NULL),
       (5, 'Maven vs Gradle', 'Which to choose?', '2025-03-05 09:05:00', 5, 2, 4, NULL);

INSERT INTO `tb_reply` (`id`, `body`, `moment`, `topic_id`, `author_id`)
VALUES (1, 'Add /usr/lib/jvm/java-17/bin to PATH and source your profile.', '2025-01-11 12:30:00', 1, 2),
       (2, 'Use List when order matters; Set for uniqueness.', '2025-01-20 19:00:00', 2, 3),
       (3, 'They are paradigms; choose based on problem domain.', '2025-02-15 15:00:00', 3, 1),
       (4, 'Singleton is default; prototype creates new bean each call.', '2025-03-20 18:00:00', 4, 5),
       (5, 'Gradle is flexible; Maven is more declarative.', '2025-03-05 10:00:00', 5, 4),
       (6, 'Check JAVA_HOME too.', '2025-01-11 12:45:00', 1, 6);

UPDATE `tb_topic`
SET `reply_id` = 1
WHERE `id` = 1;
UPDATE `tb_topic`
SET `reply_id` = 2
WHERE `id` = 2;
UPDATE `tb_topic`
SET `reply_id` = 4
WHERE `id` = 4;

INSERT INTO `tb_topics_likes` (`topic_id`, `user_id`)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 1),
       (4, 4);

INSERT INTO `tb_replies_likes` (`reply_id`, `user_id`)
VALUES (1, 1),
       (1, 3),
       (2, 1),
       (2, 4),
       (4, 2),
       (5, 5);

INSERT INTO `tb_deliver` (`id`, `uri`, `feedback`, `correct_count`, `status`, `moment`, `lesson_id`, `user_id`,
                          `offer_id`)
VALUES (1, 'https://gist.example.com/u1/l3', 'Good effort', 7, 'ACCEPTED', '2025-02-28 20:00:00', 3, 2, 1),
       (2, 'https://gist.example.com/u2/l4', 'Fix null checks', 5, 'REJECTED', '2025-03-09 21:00:00', 4, 2, 1),
       (3, 'https://gist.example.com/u1/l4', 'Solid solution', 9, 'ACCEPTED', '2025-03-10 20:00:00', 4, 1, 1),
       (4, 'https://gist.example.com/u3/l6', 'Missing tests', 6, 'PENDING', '2025-04-24 22:00:00', 6, 3, 3),
       (5, 'https://gist.example.com/u5/l2', 'Late submission', 4, 'REJECTED', '2025-04-15 08:30:00', 2, 5, 2);
