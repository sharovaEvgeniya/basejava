<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<div class="header">
    <h2 class="resume-control-h2">Resume management</h2>
</div>
<section>
    <table class="table">
        <thead>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=delete">
                        <img src="img/delete-file.png" width="25" height="25">
                    </a>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=edit">
                        <img src="img/pen.png" width="25" height="25">
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
</body>
</html>
