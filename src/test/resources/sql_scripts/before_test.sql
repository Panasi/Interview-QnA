INSERT INTO categories (name, parent_id) VALUES
('Java', null),
('Java OOP', 1),
('Java Collections', 1),
('Spring', null),
('Spring Boot', 4),
('Hibernate', null),
('PHP', null);

INSERT INTO questions (name, category_id) VALUES
('What is Java?', 1),
('What is JVM?', 1),
('What is Inheritance?', 2),
('What is ArrayList?', 3),
('What is Hibernate?', 6),
('What is Hibernate Validator?', 6);

INSERT INTO answers (name, question_id) VALUES
('Java is a programming language', 1),
('Java is OOP language', 1),
('Java is language', 1),
('Java is ...', 1);