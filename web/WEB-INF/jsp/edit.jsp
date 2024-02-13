<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.urise.webapp.model.*" %>
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
            <table>
                <c:forEach var="sectionEntry" items="${resume.sections}">
                    <jsp:useBean id="sectionEntry"
                                 type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
                    <c:set var="type" value="${sectionEntry.key}"/>
                    <c:set var="section" value="${sectionEntry.value}"/>
                    <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
                    <c:choose>
                        <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                            <dt>${type.title}</dt>
                            <dd>
                            <textarea name="${type.name()}"
                                      cols="100"><%=((TextSection) section).getContent()%></textarea>
                            </dd>
                        </c:when>
                        <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATION'}">
                            <dt>${type.title}</dt>
                            <dd>
                            <textarea name="${type.name()}"
                                      cols="100"><%=String.join("\n", ((ListSection) section).getStrings())%></textarea>
                            </dd>
                        </c:when>
                        <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                            <c:forEach var="organization"
                                       items="<%=((OrganizationSection) section).getOrganization()%>" varStatus="counter">
                                <dt>${type.title}</dt>
                                <dd>
                                    <input type="text" name="${type.name()}orgName" placeholder="company-title" size="30"
                                           value="${organization.title()}">
                                    <input type="text" name="${type.name()}website" placeholder="company-website" size="30"
                                           value="${organization.website()}">
                                </dd>
                                <c:forEach var="period" items="${organization.periods}">
                                    <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Period"/>
                                    <dd>

                                        <input type="text" name="${type.name()}${counter}startDate" placeholder="Начало" size="10"
                                               value="${period.start()}">
                                        <input type="text" name="${type.name()}${counter}endDate" placeholder="Конец" size="10"
                                               value="${period.end()}">
                                        <textarea name="${type.name()}${counter}title" cols="" placeholder="Должность">${period.title()}</textarea>
                                        <textarea name="${type.name()}${counter}description" cols="100"
                                                  placeholder="Описание">${period.description()}</textarea>
                                    </dd>
                                </c:forEach>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </table>
        </div>
        <button type="submit">Save</button>
        <button class="delete" type="reset" onclick="window.history.back()">Cancel</button>
        </div>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>