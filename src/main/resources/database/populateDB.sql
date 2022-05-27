INSERT INTO users VALUES
(1, 'testuser', 'user@email.com', '1345q'),
(2, 'testadmin', 'admin@email.com', 'qwerty123');
INSERT INTO directories VALUES
(1, 1, 'default');
INSERT INTO notes VALUES
(1, 1, 1, 'test note 1 user1', 'title 1', false),
(2, 1, 1, 'test note 2 user1', 'title 2', false),
(3, 2, 1, 'test note 1 user2', 'my first title', false),
(4, 1, 1, 'test note 3 user1', 'title 3', false);