<%@ page import="java.util.Map" %>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/formStyle.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
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
                           value="${resume.fullName}" pattern="^[^\s]+(\s.*)?$" required></dd>
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
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.Section" scope="request"/>
                <c:choose>
                    <c:when test="${type == 'OBJECTIVE'}">
                        ${System.out.println(section)}
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" placeholder="${type.title}" size=100
                                   value="${resume.toHtml(type)}"></dd>
                    </c:when>
                    <c:when test="${type == 'PERSONAL'}">
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" placeholder="${type.title}" size=100
                                   value="${resume.toHtml(type)}"></dd>
                    </c:when>

                    <c:when test="${type == 'ACHIEVEMENT'}">
                        <dt>${type.title}</dt>
                        <dd>
                            <textarea name="${type.name()}" cols="100">${resume.toHtml(type)}</textarea>
                        </dd>
                    </c:when>
                    <c:when test="${type == 'QUALIFICATION'}">

                        <dt>${type.title}</dt>
                        <dd>
                            <textarea name="${type.name()}" cols="100">${resume.toHtml(type)}</textarea>
                        </dd>
                    </c:when>
                    <c:when test="${type == 'EXPERIENCE'}">
                        <c:forEach var="org" items="${section}">
                            <%
                                OrganizationSection organizationSection = (OrganizationSection) resume.getSection(SectionType.EXPERIENCE);
                                if (organizationSection == null) {
                                }
                            %>
                        </c:forEach>
                    </c:when>
                </c:choose>

            </c:forEach>

            </p>
            <button type="submit">Save</button>
            <button class="delete" type="reset" onclick="window.history.back()">Cancel</button>
        </div>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>