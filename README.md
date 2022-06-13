# Todo
Test project for educational purposes

API address:\
https://todo.coldwinternight.ru/todo/

## Section users:
https://todo.coldwinternight.ru/todo/users

### Create new user:
POST http://todo.coldwinternight.ru/todo/users \
*awaits json*
```json
{
"username": "Matt2",
"email": "mattuser2@mail.com",
"password": "123mattpass2"
}
```

### Edit user:
PATCH http://todo.coldwinternight.ru/todo/users/{id} \
*awaits json*
```json
{
"username": "Matt2",
"email": "mattuser2@mail.com",
"password": "123mattpass2"
}
```

### Delete user:
DELETE http://todo.coldwinternight.ru/todo/users/{id} \


## Section tasks:
https://todo.coldwinternight.ru/todo/tasks

### All user tasks:
GET http://todo.coldwinternight.ru/todo/tasks?userid={id}

### Get one task by ID:
GET http://todo.coldwinternight.ru/todo/tasks/{id}

### Create new task for user by ID:
POST http://todo.coldwinternight.ru/todo/tasks?userid={id} \
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
PUT http://todo.coldwinternight.ru/todo/tasks/{id}

### Edit task:
PATCH http://todo.coldwinternight.ru/todo/tasks/{id}
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
DELETE http://todo.coldwinternight.ru/todo/tasks/{id}