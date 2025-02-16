<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Профиль</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f8f9fa;
      color: #343a40;
      margin: 0;
      padding: 20px;
    }
    .container {
      background: #ffffff;
      padding: 20px;
      border-radius: 5px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }
    .avatar {
      width: 150px;
      height: 150px;
      border-radius: 50%;
      margin-bottom: 20px;
    }
    .navigation a {
      margin-right: 10px;
      text-decoration: none;
      color: #007bff;
    }
    .navigation a:hover {
      text-decoration: underline;
    }
    .search-form {
      display: flex;
      align-items: center;
      gap: 10px;
      margin-bottom: 20px;
    }
    .search-form input[type="text"] {
      width: 300px;
      padding: 5px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    .search-form button {
      padding: 5px 10px;
      background-color: #007bff;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    .search-form button:hover {
      background-color: #0056b3;
    }
    .actions a {
      display: inline-block;
      margin-right: 10px;
      padding: 5px 10px;
      border-radius: 5px;
      color: white;
      text-decoration: none;
      font-weight: bold;
    }
    .btn-green {
      background-color: #28a745;
    }
    .btn-red {
      background-color: #dc3545;
    }
    .btn-green:hover {
      background-color: #218838;
    }
    .btn-red:hover {
      background-color: #c82333;
    }
    .popular-projects {
      list-style-type: none;
      padding: 0;
    }
    .popular-projects li {
      background: #f1f3f5;
      margin: 10px 0;
      padding: 10px;
      border-radius: 3px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
    .popular-projects li a {
      color: #007bff;
      text-decoration: none;
    }
    .popular-projects li a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="navigation">
    <a href="<c:url value='/index' />">На главную</a>
    <form action="<c:url value='/search' />" method="get" class="search-form">
      <input type="text" name="text" placeholder="Введите запрос для поиска" required />
      <button type="submit">Искать</button>
    </form>
    <div class="actions">
      <a href="<c:url value='/create' />" class="btn-green">Создать проект</a>
      <a href="<c:url value='/logout' />" class="btn-red">Выйти</a>
    </div>
  </div>

  <h1>Профиль</h1>
  <img src="<c:out value='${avatar}' />" alt="Аватар" class="avatar">
  <form action="<c:url value='/avatarupload' />" method="post" enctype="multipart/form-data">
    <input type="file" name="avatar" accept="image/*" required>
    <button type="submit">Загрузить</button>
  </form>

  <h2>Имя: <c:out value="${name}" /></h2>
  <h3>Username: @<c:out value="${username}" /></h3>

  <h2>Самые популярные проекты:</h2>
  <ul class="popular-projects">
    <c:choose>
      <c:when test="${not empty projects}">
        <c:forEach var="project" items="${projects}">
          <li>
            <a href="<c:url value='/project?id=${project.id}' />">
              <c:out value="${project.name}" />
            </a>
          </li>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <li>У пользователя пока нет проектов</li>
      </c:otherwise>
    </c:choose>
  </ul>
</div>
</body>
</html>
