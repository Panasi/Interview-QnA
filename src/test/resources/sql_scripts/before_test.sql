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

INSERT INTO questions (name, user_id, category_id, is_private) VALUES
('Admin public question', 1, 1, false),
('Admin private question', 1, 2, true),
('User1 public question', 2, 3, false),
('User1 private question', 2, 1, true),
('User2 public question', 3, 2, false),
('User2 private question', 3, 3, true),
('Admin question for update', 1, 3, true),
('User1 question for update', 2, 3, true),
('User2 question for update', 3, 3, true),
('Admin question for delete', 1, 3, true),
('User1 question for delete', 2, 3, true),
('User2 question for delete', 3, 3, true);

INSERT INTO answers (name, user_id, question_id, is_private) VALUES
('Admin public answer', 1, 1, false),
('Admin private answer', 1, 1, true),
('User1 public answer', 2, 1, false),
('User1 private answer', 2, 1, true),
('User2 public answer', 3, 1, false),
('User2 private answer', 3, 1, true),
('Admin answer for update', 1, 1, true),
('User1 answer for update', 2, 1, true),
('User2 answer for update', 3, 1, true),
('Admin answer for delete', 1, 1, true),
('User1 answer for delete', 2, 1, true),
('User2 answer for delete', 3, 1, true);

INSERT INTO question_comments (content, rate, question_id, user_id) VALUES
('Comment1', 1, 1, 2),
('Comment2', 2, 1, 2),
('Comment3', 3, 2, 3),
('Comment4', 4, 2, 3),
('Comment5', 5, 4, 3),
('Comment6', 5, 6, 3),
('Comment7', 5, 6, 3);

INSERT INTO answer_comments (content, rate, answer_id, user_id) VALUES
('Comment1', 1, 1, 2),
('Comment2', 2, 1, 2),
('Comment3', 3, 2, 3),
('Comment4', 4, 2, 3),
('Comment5', 5, 4, 3),
('Comment6', 5, 6, 3),
('Comment7', 5, 6, 3);
