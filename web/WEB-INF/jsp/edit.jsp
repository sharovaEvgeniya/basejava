<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
                <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
            <dl class="section-title">
                <dt>${type.title}</dt>
                <c:choose>
                    <c:when test="${type == 'OBJECTIVE'}">
                        <dd><input type="text" name="${type}" size="75"
                                   value="<%=((TextSection)section).getContent()%>"></dd>
                    </c:when>
                    <c:when test="${type == 'PERSONAL'}">
                        <dd><textarea name="${type}" cols="75"
                                      rows="5"><%=((TextSection) section).getContent()%></textarea></dd>
                    </c:when>
                    <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATION'}">
                        <dd><textarea name="${type.name()}" cols="75"
                                      rows="5"><%=String.join("\n", ((ListSection) section).getStrings())%>
                        </textarea></dd>
                    </c:when>
                    <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                        <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>"
                                   varStatus="counter">
                            <dl>
                                <dt>Название учереждения:</dt>
                                <dd><input type="text" name="${type.name()}orgName" size="100" value="${org.title}">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Сайт учереждения:</dt>
                                <dd><input type="text" name="${type.name()}link" size="100" value="${org.url}"></dd>
                            </dl>
                            <br>
                            <div style="margin-left: 30px">
                                <c:forEach var="period" items="${org.periods}">
                                    <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Period"/>
                                    <dl>
                                        <dt>Начальная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type.name()}${counter.index}startDate" size="10"
                                                   value="<%=DateUtil.format(period.getStart())%>"
                                                   placeholder="MM/yyyy">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Конечная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type.name()}${counter.index}endDate" size="10"
                                                   value="<%=DateUtil.format(period.getEnd())%>"
                                                   placeholder="MM/yyyy">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd>
                                            <input type="text" name="${type.name()}${counter.index}position" size="75"
                                                   value="${period.title}">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd>
                                            <textarea name="${type.name()}${counter.index}description" rows="2"
                                                      cols="75">${period.description}</textarea>
                                        </dd>
                                    </dl>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </dl>
            </c:forEach>
            <button type="submit">Save</button>
            <button class="delete" type="reset" onclick="window.history.back()">Cancel</button>

    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>