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

INSERT INTO answers (name, question_id, is_private) VALUES
('Java is a programming language', 1, true),
('Java is OOP language', 1, false),
('Java is language', 1, false),
('Java is ...', 1, false);

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