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
<jsp:include page="fragments/header.jsp"/>
<div class="add-resume">
    <a href="resume" style="text-decoration: none">
        <img src="img/add-resume.png" width="30" height="30" style="vertical-align:sub">
    </a>
    <a href="resume?action=add">
        Add resume
    </a>
</div>
<div class="tables">
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Edit</th>
            <th>Delete</th>
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
                    <a href="resume?uuid=${resume.uuid}&action=edit">
                        <img src="img/pen.png" width="25" height="25">
                    </a>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=delete">
                        <img src="img/delete.png" width="25" height="25">
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
