INSERT INTO users VALUES
(1, 'testuser', 'user@email.com', '$2a$12$3b8C/2afZf/FdlRYcIGsiuW75BXFZ8iQAZXLw4t42DYbKdAEY9iVC', 3, false),
-- 1345q
(2, 'testadmin', 'admin@email.com', '$2a$12$j1QTNJauUDNcEbBI5I13kupTAGzMehw5BpPMhDlLHcWov8SMsEq6i', 2, false);
-- qwerty123
INSERT INTO tasks VALUES
(1, 1, 'test task 1 user1', 'title 1', false, false),
(2, 1, 'test task 2 user1', 'title 2', false, false),
(3, 2, 'test task 1 user2', 'my first title', false, false),
(4, 1, 'test task 3 user1', 'title 3', false, false);