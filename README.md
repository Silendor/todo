# Todo
Test project for educational purposes

Homepage: \
https://todo.coldwinternight.ru/

API address:\
https://todo.coldwinternight.ru/api/

## Section login:
https://todo.coldwinternight.ru/api/login

Email and Password field are strongly required.

POST https://todo.coldwinternight.ru/api/login \
*expects application/x-www-form-urlencoded*
```
email: "example@email.com"
password: "examplepassword"
```

## Section users:
https://todo.coldwinternight.ru/api/users \
*expects header with token*
```
Authorization : Bearer $token
```


### Create new user:
POST https://todo.coldwinternight.ru/api/users \
Email and Password field are strongly required.


*expects json*
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
  "email": "mattuser2@mail.com",
  "password": "123mattpass2"
}
```

### Edit user:
PUT https://todo.coldwinternight.ru/api/users \
Email field are strongly required. \
All optional fields will take default values if not specified. \
Password field can be changed via special request.

*expects json*
```json
{
  "username": "guideTheNPC",
  "email": "guide@mail.com",
  "todayAmount": 3,
  "randomizeTodayTasks": false
}
```

### Reverse user 'randomize_today_tasks' field:
PATCH https://todo.coldwinternight.ru/api/users/reverseRandomizeTasks \
*answer will provide current status after change*

### Edit password:
PATCH https://todo.coldwinternight.ru/api/users/password \
*expects json*
```json
{
  "oldpassword": "actual password",
  "newpassword": "new password"
}
```

### Edit username:
PATCH https://todo.coldwinternight.ru/api/users/username \
*expects json*
```json
{
  "username": "new username"
}
```

### Delete user:
DELETE https://todo.coldwinternight.ru/api/users \


## Section tasks:
https://todo.coldwinternight.ru/api/tasks \
*expects header with token*
```
Authorization : Bearer $token
```

### All user tasks for user:
GET https://todo.coldwinternight.ru/api/tasks

### Get one task by ID:
GET https://todo.coldwinternight.ru/api/tasks/{id}

### Get all today tasks for user:
GET https://todo.coldwinternight.ru/api/tasks/today

### Get all completed tasks for user:
GET https://todo.coldwinternight.ru/api/tasks/completed

### Create new task for user:
POST https://todo.coldwinternight.ru/api/tasks \
Title field are strongly required. \
All optional fields will take default values if not specified.

*expects json*
```json
{
  "title": "test title",
  "task_body": "test todo",
  "today": false
}
```
or
```json
{
  "title": "test title"
}
```

### Reverse task 'completed' field:
PATCH https://todo.coldwinternight.ru/api/tasks/{id}/reverseCompleted \
*answer will provide current status after change*

### Reverse task 'today' field:
PATCH https://todo.coldwinternight.ru/api/tasks/{id}/reverseToday \
*answer will provide current status after change*

### Reverse archived 'archived' field:
PATCH https://todo.coldwinternight.ru/api/tasks/{id}/reverseArchived \
*answer will provide current status after change*

### Edit task:
PUT https://todo.coldwinternight.ru/api/tasks/{id} \
Title field are strongly required. \
All optional fields will take default values if not specified.

*expects json*
```json
{
  "title": "title test change",
  "taskBody": "changed task body",
  "completed": false,
  "today": false,
  "archived": false
}
```
or
```json
{
  "title": "test title",
  "task_body": "test todo"
}
```

### Edit title and task body:
PATCH https://todo.coldwinternight.ru/api/tasks/{id}/titleAndBody \
*expects json*
```json
{
  "title": "new title",
  "task_body": "test todo"
}
```
Task body can be empty string.

### Delete task:
DELETE https://todo.coldwinternight.ru/api/tasks/{id}

### Delete all today tasks:
DELETE https://todo.coldwinternight.ru/api/tasks/today