CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY,
    username VARCHAR(200),
    email VARCHAR(254) NOT NULL,
    password VARCHAR(200) NOT NULL,
    today_amount INTEGER NOT NULL default 3,
    randomize_today_tasks BOOLEAN NOT NULL default false,
    UNIQUE (email),
    CHECK ( email != '' AND password != '' )
--                                  ,
--     reg_date TIMESTAMP,
--     last_login TIMESTAMP
);
CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 3 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS tasks (
    id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    task_body VARCHAR(254) NOT NULL,
    title VARCHAR(254) NOT NULL,
    completed BOOLEAN NOT NULL default false,
    today BOOLEAN NOT NULL default false,
    CHECK ( task_body != '' AND title != '' ),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE SEQUENCE IF NOT EXISTS task_id_seq START WITH 5 INCREMENT BY 1;