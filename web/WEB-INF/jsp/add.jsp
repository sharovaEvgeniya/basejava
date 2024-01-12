<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Новое резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form class="decor" method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <div class="form-inner">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <h3>Full name:</h3>
            <dl>
                <dd><input type="text" name="fullName" placeholder="Фамилия Имя Отчество" size=35
                           value="${resume.fullName}"></dd>
            </dl>
            <h3>Contacts:</h3>
            <p>
                <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" placeholder="${type.title}" size=30
                           value="${resume.getContact(type)}"></dd>
            </dl>
            </c:forEach>
            <h3>Sections:</h3>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <dl>
                    <dt>${type.title}</dt>
                    <dd><input type="text" name="${type.name()}" size="100" value="${resume.toHtml(type)}"></dd>
                </dl>
            </c:forEach>

            </p>
            <button type="submit">Save</button>
            <button class="delete" onclick="window.history.back()">Cancel</button>
        </div>
    </form>
</section>
</body>
</html>
