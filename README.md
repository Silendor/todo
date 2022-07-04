# Todo
Test project for educational purposes

API address:\
https://todo.coldwinternight.ru/api/

## Section login:
https://todo.coldwinternight.ru/api/login

POST https://todo.coldwinternight.ru/api/login \
*awaits application/x-www-form-urlencoded*
```
email: "example@email.com"
password: "examplepassword"
```

## Section users:
https://todo.coldwinternight.ru/api/users

### Create new user:
POST http://todo.coldwinternight.ru/api/users \
*awaits json*
```json
{
"username": "Matt2",
"email": "mattuser2@mail.com",
"password": "123mattpass2"
}
```

### Edit user:
PATCH http://todo.coldwinternight.ru/api/users/{id} \
*awaits json*
```json
{
"username": "Matt2",
"email": "mattuser2@mail.com",
"password": "123mattpass2"
}
```

### Delete user:
DELETE http://todo.coldwinternight.ru/api/users/{id} \


## Section notes:
https://todo.coldwinternight.ru/api/notes

### All user notes:
GET http://todo.coldwinternight.ru/api/notes?userid={id}

### Get one task by ID:
GET http://todo.coldwinternight.ru/api/notes/{id}

### Create new task for user by ID:
POST http://todo.coldwinternight.ru/api/notes?userid={id} \
*awaits json*
```json
{
    "user_id": 2,
    "directory_id": null,
    "task": "test todo",
    "title": "test title",
    "completed": false
}
```

### Reverse task status (completed/not completed):
PUT http://todo.coldwinternight.ru/api/notes/{id}

### Edit task:
PATCH http://todo.coldwinternight.ru/api/notes/{id}
*awaits json*
```json
{
    "user_id": 2,
    "directory_id": null,
    "task": "test todo",
    "title": "test title",
    "completed": false
}
```

### Delete task:
DELETE http://todo.coldwinternight.ru/api/notes/{id}


## Section directories:
https://todo.coldwinternight.ru/api/directories

### Get one directory by ID:
GET http://todo.coldwinternight.ru/api/directories/{id}

### Create a new directory for user by ID:
POST http://todo.coldwinternight.ru/api/directories?userid={id} \
*awaits json*
```json
{
    "name": "new dir"
}
```

### Edit directory:
PATCH http://todo.coldwinternight.ru/api/directories/{id} \
*awaits json*
```json
{
    "name": "edited dir"
}
```

### Delete a directory:
DELETE http://todo.coldwinternight.ru/api/directories/{id}