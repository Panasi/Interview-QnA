INSERT INTO users (username, email, password) VALUES
('Admin', 'admin@gmail.com', 'password'),
('User1', 'user1@gmail.com', 'password'),
('User2', 'user2@gmail.com', 'password');

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 2),
(2, 1),
(3, 1);

INSERT INTO categories (name, parent_id) VALUES
('Category1', null),
('Category2', 1),
('Category3', null),
('Category4', null),
('Category5', null);

INSERT INTO questions (name, user_id, category_id, is_private, date) VALUES
('Admin public question',  1, 1, false, '2023-01-27T10:53:00.092804'),
('Admin private question', 1, 2, true, '2023-01-27T10:52:00.092804'),
('User1 public question',  2, 3, false, '2023-01-27T10:54:00.092804'),
('User1 private question', 2, 1, true, '2023-01-27T10:55:00.092804'),
('User2 public question',  3, 2, false, '2023-01-27T10:52:00.092804'),
('User2 private question', 3, 3, true, '2023-01-27T10:47:00.092804');

INSERT INTO answers (name, user_id, question_id, is_private, date) VALUES
('Admin public answer',  1, 3, false, '2023-01-27T10:53:00.092804'),
('Admin private answer', 1, 1, true, '2023-01-27T10:52:00.092804'),
('User1 public answer',  2, 2, false, '2023-01-27T10:54:00.092804'),
('User1 private answer', 2, 1, true, '2023-01-27T10:55:00.092804'),
('User2 public answer',  3, 2, false, '2023-01-27T10:52:00.092804'),
('User2 private answer', 3, 2, true, '2023-01-27T10:47:00.092804');

INSERT INTO question_comments (content, rate, question_id, user_id, date) VALUES
('Comment1', 1, 1, 2, '2023-01-27T10:53:00.092804'),
('Comment2', 2, 1, 2, '2023-01-27T10:52:00.092804'),
('Comment3', 3, 2, 3, '2023-01-27T10:54:00.092804'),
('Comment4', 4, 2, 3, '2023-01-27T10:55:00.092804'),
('Comment5', 5, 4, 3, '2023-01-27T10:52:00.092804'),
('Comment6', 5, 6, 3, '2023-01-27T10:47:00.092804'),
('Comment7', 5, 6, 3, '2023-01-27T10:45:00.092804');

INSERT INTO answer_comments (content, rate, answer_id, user_id, date) VALUES
('Comment1', 1, 1, 2, '2023-01-27T10:53:00.092804'),
('Comment2', 2, 1, 2, '2023-01-27T10:52:00.092804'),
('Comment3', 3, 2, 3, '2023-01-27T10:54:00.092804'),
('Comment4', 4, 2, 3, '2023-01-27T10:55:00.092804'),
('Comment5', 5, 4, 3, '2023-01-27T10:52:00.092804'),
('Comment6', 5, 6, 3, '2023-01-27T10:47:00.092804'),
('Comment7', 5, 6, 3, '2023-01-27T10:45:00.092804');
