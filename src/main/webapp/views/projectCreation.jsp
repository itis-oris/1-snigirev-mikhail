<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Создание проекта</title>
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      width: 50%;
      margin: 0 auto;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
    .container h1 {
      text-align: center;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
    }
    .form-group input,
    .form-group textarea,
    .form-group select {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    .form-group input[type="file"] {
      padding: 3px;
    }
    .form-group button {
      width: 100%;
      padding: 10px;
      background-color: #007BFF;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    .form-group button:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Создание нового проекта</h1>
  <form action="createProject" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="projectName">Название проекта:</label>
      <input type="text" id="projectName" name="projectName" required>
    </div>
    <div class="form-group">
      <label for="description">Описание проекта:</label>
      <textarea id="description" name="description" rows="5" required></textarea>
    </div>
    <div class="form-group">
      <label for="teacherName">Преподаватель:</label>
      <input type="text" id="teacherName" name="teacherName" required>
    </div>
    <div class="form-group">
      <label for="subjectName">Предмет:</label>
      <input type="text" id="subjectName" name="subjectName" required>
    </div>
    <div class="form-group">
      <label for="instituteName">Институт:</label>
      <input type="text" id="instituteName" name="instituteName" required>
    </div>
    <div class="form-group">
      <label for="year">Год:</label>
      <input type="number" id="year" name="year" required>
    </div>
    <div class="form-group">
      <label for="avatar">Аватар проекта (необязательно):</label>
      <input type="file" id="avatar" name="avatar" accept="image/*">
    </div>
    <div class="form-group">
      <button type="submit">Создать проект</button>
    </div>
  </form>
</div>
</body>
</html>
