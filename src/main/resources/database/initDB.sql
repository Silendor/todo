CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY,
    username VARCHAR(200),
    email VARCHAR(254) NOT NULL,
    password VARCHAR(200) NOT NULL,
    UNIQUE (email),
    CHECK ( email != '' AND password != '' )
--                                  ,
--     reg_date TIMESTAMP,
--     last_login TIMESTAMP
);
CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 3 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS directories (
   id INTEGER PRIMARY KEY,
   user_id INTEGER NOT NULL,
   name VARCHAR(254) NOT NULL,
   CHECK ( name != '' ),
   FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE SEQUENCE IF NOT EXISTS directory_id_seq START WITH 2 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS notes (
    id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    directory_id INTEGER,
    note VARCHAR(254) NOT NULL,
    title VARCHAR(254) NOT NULL,
    completed BOOLEAN NOT NULL,
    CHECK ( note != '' AND title != '' ),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (directory_id) REFERENCES directories (id)
);
CREATE SEQUENCE IF NOT EXISTS note_id_seq START WITH 5 INCREMENT BY 1;