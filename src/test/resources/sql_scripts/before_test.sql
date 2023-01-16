INSERT INTO users (username, email, password) VALUES
('Admin', 'admin@gmail.com', 'password');

INSERT INTO user_roles (id, user_id, role_id) VALUES
(1, 1, 2);

INSERT INTO categories (name, parent_id) VALUES
('Java', null),
('Java OOP', 1),
('Java Collections', 1),
('Spring', null),
('Spring Boot', 4),
('Hibernate', null),
('PHP', null);

INSERT INTO questions (name, category_id, is_private) VALUES
('What is Java?', 1, true),
('What is JVM?', 1, true),
('What is Inheritance?', 2, false),
('What is ArrayList?', 3, false),
('What is Hibernate?', 6, false),
('What is Hibernate Validator?', 6, false);

INSERT INTO answers (name, user_id, question_id, is_private) VALUES
('Admin public answer', 1, 1, false),
('Admin private answer', 1, 1, true),
('User1 public answer', 2, 1, false),
('User1 private answer', 2, 1, true),
('User2 public answer', 3, 1, false),
('User2 private answer', 3, 1, true),
('Admin answer for update', 1, 1, false),
('User1 answer for update', 2, 1, false),
('User2 answer for update', 3, 1, false),
('Admin answer for delete', 1, 1, false),
('User1 answer for delete', 2, 1, false),
('User2 answer for delete', 3, 1, false);

INSERT INTO answer_comments (content, rate, answer_id) VALUES
('This in not a complete answer.', 3, 1),
('Not bad.', 5, 1),
('WTF?', 1, 2),
('Perfect!', 5, 2);

INSERT INTO question_comments (content, rate, question_id) VALUES
('I dont understand this question.', 3, 1),
('Stupid question.', 1, 1),
('WTF?', 1, 2),
('Perfect!', 5, 2);