<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Начальная страница</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        .container {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .project-list {
            list-style-type: none;
            padding: 0;
        }
        .project-list li {
            background: #e2e2e2;
            margin: 10px 0;
            padding: 10px;
            border-radius: 3px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Начальная страница</h1>

    <h2>Популярные проекты:</h2>
    <ul class="project-list">
        <c:choose>
            <c:when test="${not empty projects}">
                <c:forEach var="project" items="${projects}">
                    <li>
                        <a href="${pageContext.request.contextPath}/project?id=${project.id}">
                                ${project.name}
                        </a>
                    </li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li>К сожалению, на сайте пока нет проектов</li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
</body>
</html>
