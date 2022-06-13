# Todo
Test project for educational purposes

API address:\
https://todo.coldwinternight.ru/todo/

## Section users:
https://todo.coldwinternight.ru/todo/users

### Create new user:
POST https://todo.coldwinternight.ru/todo/users \
*awaits json*
```json
{
  "username": "Matt2",
  "email": "mattuser2@mail.com",
  "password": "123mattpass2",
  "today_amount": 3,
  "randomize_today_tasks": false
}
```
or
```json
{
  "username": "Matt2",
  "email": "mattuser2@mail.com",
  "password": "123mattpass2"
}
```

### Edit user:
PATCH https://todo.coldwinternight.ru/todo/users/{id} \
*awaits json*
```json
{
  "username": "Matt2",
  "email": "mattuser2@mail.com",
  "password": "123mattpass2",
  "today_amount": 3,
  "randomize_today_tasks": false
}
```
or
```json
{
  "username": "Matt2",
  "email": "mattuser2@mail.com",
  "password": "123mattpass2"
}
```

### Edit password:
PATCH https://todo.coldwinternight.ru/todo/users/{id}/password \
*awaits json*
```json
{
  "oldpassword": "actual password",
  "newpassword": "new password"
}
```

### Delete user:
DELETE https://todo.coldwinternight.ru/todo/users/{id} \


## Section tasks:
https://todo.coldwinternight.ru/todo/tasks

### All user tasks:
GET https://todo.coldwinternight.ru/todo/tasks?userid={id}

### Get one task by ID:
GET https://todo.coldwinternight.ru/todo/tasks/{id}

### Create new task for user by ID:
POST https://todo.coldwinternight.ru/todo/tasks?userid={id} \
*awaits json*
```json
{
  "user_id": 2,
  "title": "test title",
  "task_body": "test todo",
  "completed": false,
  "today": false
}
```
or
```json
{
  "user_id": 2,
  "title": "test title",
  "task_body": "test todo"
}
```

### Reverse task status (completed/not completed):
PUT https://todo.coldwinternight.ru/todo/tasks/{id}

### Edit task:
PATCH https://todo.coldwinternight.ru/todo/tasks/{id}
*awaits json*
```json
{
  "user_id": 2,
  "title": "test title",
  "task_body": "test todo",
  "completed": false,
  "today": false
}
```
or
```json
{
  "user_id": 2,
  "title": "test title",
  "task_body": "test todo"
}
```

### Delete task:
DELETE https://todo.coldwinternight.ru/todo/tasks/{id}