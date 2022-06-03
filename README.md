# Todo
Test project for educational purposes

API address:\
https://todo.coldwinternight.ru/todo/

##Section users: \
https://todo.coldwinternight.ru/todo/users

###Add new user: \
POST http://todo.coldwinternight.ru/todo/users \
*awaits json*
```json
{
"username": "Matt2",
"email": "mattuser2@mail.com",
"password": "123mattpass2"
}
```

###Edit user: \
PATCH http://todo.coldwinternight.ru/todo/users/{id} \
*awaits json*
```json
{
"username": "Matt2",
"email": "mattuser2@mail.com",
"password": "123mattpass2"
}
```

###Delete user: \
DELETE http://todo.coldwinternight.ru/todo/users/{id} \


##Section notes: \
https://todo.coldwinternight.ru/todo/notes

###All user notes:
GET http://todo.coldwinternight.ru/todo/notes?userid={id}

###Get one note by ID:
GET http://todo.coldwinternight.ru/todo/notes/{id}

###Add new note for user by ID:
POST http://todo.coldwinternight.ru/todo/notes?userid={id} \
*awaits json*
```json
{
    "user_id": 2,
    "directory_id": null,
    "note": "test todo",
    "title": "test title",
    "completed": false
}
```

###Reverse note status (completed/not completed):
PUT http://todo.coldwinternight.ru/todo/notes/{id}

###Edit note:
PATCH http://todo.coldwinternight.ru/todo/notes/{id}
*awaits json*
```json
{
    "user_id": 2,
    "directory_id": null,
    "note": "test todo",
    "title": "test title",
    "completed": false
}
```

###Delete note:
DELETE http://todo.coldwinternight.ru/todo/notes/{id}


##Section directories: \
https://todo.coldwinternight.ru/todo/directories

###Get one directory by ID:
GET http://todo.coldwinternight.ru/todo/directories/{id}

###Add new directory for user by ID:
POST http://todo.coldwinternight.ru/todo/directories?userid={id} \
*awaits json*
```json
{
    "name": "new dir"
}
```

###Edit directory:
PATCH http://todo.coldwinternight.ru/todo/directories/{id} \
*awaits json*
```json
{
    "name": "edited dir"
}
```

###Delete directory:
DELETE http://todo.coldwinternight.ru/todo/directories/{id}