# Список дел

Список дел с пейджингом.

_Стек технологий_: Maven/Spring/Hibernate/Tomcat/MySQL/Spring MVC

`Функции`
- CRUD
- Пейджинг по 10 записей на странице
- Фильтрация списка по критериям «Все дела», «Только невыполненные», «Выполненные»

------------------

Создайте базу MySQL, выполните

        import.sql

Сборка проекта

         mvn clean package

Запуск (из папки проекта)

         java -jar ToDoList-1.0.war --server.port=[PORT]

В браузере (вместо [PORT] укажите доступный http порт)

         http://localhost:[PORT]/todolist.html
