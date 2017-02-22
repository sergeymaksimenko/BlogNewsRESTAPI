В папке main/java/resources/SQL в файле schema_blog_news.sql запросы для создания схемы с таблицами

В качестве вубсервера используется Tomcat9.0.0.M11

Для тестирования использовал дополнение REST Console для Chrome

####Примеры запросов:
#####Авторизация и получение токена:
```
Request URI : http://localhost:8080/authentication?username=admin&password=123456
Request method : POST
Accept : application/json
Content-Type: application/x-www-form-urlencoded
```


#####Создание статьи:
```
Request URI : http://localhost:8080/post
Request method : POST
Accept : application/json
Content-Type: application/json
Request parametrs:
token:[token полученный при авторизации]
version: 123
Request body : 
{
"post":{
"userCreatorId":"1",
"body":"Я создал REST приложение"
}}
```
#####Редактирование статьи:
```
Request URI : http://localhost:8080/post
Request method : PUT
Accept : application/json
Content-Type: application/json
Request parametrs:
token:[token полученный при авторизации]
version: 123
Request body : 
{
"post":{
"id":"6",
"userCreatorId":"1",
"body":"Изменяем текст статьи"
}}
```
#####Получение статьи:
```
Request URI : http://localhost:8080/post/8
Request method : GET
Accept : application/json
Content-Type: application/json
Request parametrs:
token:[token полученный при авторизации]
version: 123
Response body : 
{
    "post": {
        "body": "Я создал REST приложение",
        "dateCreated": "2017-02-22T00:00:00+02:00",
        "id": 8,
        "userCreatorId": 1
    }
}
```
#####Удаление статьи:
```
Request URI : http://localhost:8080/post/8
Request method : DELETE
Accept : application/json
Content-Type: application/json
Request parametrs:
token:[token полученный при авторизации]
version: 123
```

#####Создание комментария:
```
Request URI : http://localhost:8080/comment
Request method : POST
Accept : application/json
Content-Type: application/json
Request parametrs:
token:[token полученный при авторизации]
version: 123
Request body : 
{
"comment":{
"idPost":"8",
"userCreatorId":"1",
"body":"Комментарий к статье"
}}
```
#####Редактирование комментария:
```
Request URI : http://localhost:8080/comment/1
Request method : PUT
Accept : application/json
Content-Type: application/json
Request parametrs:
token:[token полученный при авторизации]
version: 123
Request body : 
{
"comment":{
"id":"1",
"idPost":"8",
"userCreatorId":"1",
"body":"Комментарий к статье изменен"
}}
```
#####Получение комментариев к статье:
```
Request URI : http://localhost:8080/comment/8
Request method : GET
Accept : application/json
Content-Type: application/json
Request parametrs:
token:[token полученный при авторизации]
version: 123
Response body : 
{
     "comment": [{
        "body": "Комментарий к статье изменен",
        "dateCreated": "2017-02-23T00:00:00+02:00",
        "id": 1,
        "idPost": 8,
        "userCreatorId": 1
    }]
}
```
#####Удаление комментария:
```
Request URI : http://localhost:8080/comment/8
Request method : DELETE
Accept : application/json
Content-Type: application/json
Request parametrs:
token:[token полученный при авторизации]
version: 123
```

