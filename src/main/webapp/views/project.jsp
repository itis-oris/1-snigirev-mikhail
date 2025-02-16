<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${projectName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/project.css">
    <style>
        .folder, .file {
            cursor: pointer;
            padding: 5px;
            margin-left: 20px;
        }
        .folder {
            font-weight: bold;
        }
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/index" style="text-decoration: none; color: #000; font-weight: bold; margin-right: 10px;">
    На главную
</a>
<form action="${pageContext.request.contextPath}/search" method="get" style="flex-grow: 1; display: flex; align-items: center; justify-content: center;">
    <input type="text" name="text" placeholder="Введите запрос для поиска"
           style="width: 300px; padding: 5px; border: 1px solid #ccc; border-radius: 5px;" required />
    <button type="submit" style="padding: 5px 10px; margin-left: 10px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer;">
        Искать
    </button>
</form>

<div style="display: flex; align-items: center; gap: 10px;">
    <a href="${pageContext.request.contextPath}/create"
       style="text-decoration: none; color: #fff; background-color: #28a745; padding: 5px 10px; border-radius: 5px; font-weight: bold;">
        Создать новый проект
    </a>

    <a href="${pageContext.request.contextPath}/logout"
       style="text-decoration: none; color: #fff; background-color: #dc3545; padding: 5px 10px; border-radius: 5px; font-weight: bold;">
        Выйти из аккаунта
    </a>
</div>
</div>
<div class="container">
    <div style="margin-top: 20px;">
        <c:if test="${isCreator}">
            <h2>Добавить администратора</h2>
            <form action="${pageContext.request.contextPath}/addAdmin?id=${id}" method="post" style="display: flex; align-items: center; gap: 10px;">
                <input type="text" name="username" placeholder="Введите имя пользователя"
                       style="padding: 5px; border: 1px solid #ccc; border-radius: 5px;" required />
                <button type="submit" style="padding: 5px 10px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer;">
                    Добавить
                </button>
            </form>
        </c:if>
        <strong>${message}</strong>
    </div>
    <div class="header">
        <h1>${projectName}</h1>
        <div class="avatar">
            <img src="<c:out value='${avatarUrl}' />" alt="Аватар" class="avatar">
        </div>
        <c:if test="${isAdmin}">
            <form action="${pageContext.request.contextPath}/projectavatarupload" method="post" enctype="multipart/form-data">
                <input type="hidden" name="projectId" value="${id}">
                <label for="avatar">Загрузить новый аватар:</label>
                <input type="file" id="avatar" name="avatar" accept="image/*" required>
                <button type="submit">Обновить аватар</button>
            </form>
        </c:if>
    </div>


    <div class="creator">
        <strong>Создатель:</strong>
        ${creatorName}
    </div>


    <p class="description">${description}</p>

    <div class="tags">
        <span class="tag">
        Преподаватель: ${teacherName}
        </span>
        <span class="tag">
        Институт: ${instituteName}
        </span>
        <span class="tag">
        Год: ${year}
        </span>
        <span class="tag">
        Предмет: ${subjectName}
        </span>
    </div>

    <h2>Материалы проекта</h2>

    <div id="file-container"></div>
</div>

<script>
    const json = '<%=request.getAttribute("json")%>';
    const context = '${pageContext.request.contextPath}';
    const projectStructure = JSON.parse(json);

    renderStructure(projectStructure, document.getElementById('file-container'));
    function renderStructure(data, container) {
        if (!data) return;

        if (data.files) {
            data.files.forEach(file => {
                const fileElement = document.createElement('div');
                fileElement.className = 'file';
                fileElement.textContent = file.name;
                fileElement.onclick = () => openFile(context + '/' + file.path);
                container.appendChild(fileElement);
            });
        }

        if (data.folders) {
            data.folders.forEach(folder => {
                const folderElement = document.createElement('div');
                folderElement.className = 'folder';
                folderElement.textContent = folder.path.split('/').pop();

                const folderContent = document.createElement('div');
                folderContent.className = 'hidden';

                folderElement.onclick = () => {
                    folderContent.classList.toggle('hidden');
                    if (!folderContent.hasChildNodes()) {
                        renderStructure(folder, folderContent);
                    }
                };

                container.appendChild(folderElement);
                container.appendChild(folderContent);
            });
        }
    }

    function openFile(path) {
        window.location.href = path;
    }
</script>
</body>
</html>
