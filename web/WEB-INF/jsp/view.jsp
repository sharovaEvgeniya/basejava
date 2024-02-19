<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="form">
    <div class="full-name">
        ${resume.fullName}&nbsp;
        <a href="resume?uuid=${resume.uuid}&action=edit">
            <img src="img/pen.png" width="25" height="25">
        </a>
    </div>
    <hr>
    <div class="contacts">
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </div>
    <hr>
    <div class="sections">
        <table>
            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
                <c:set var="section" value="${sectionEntry.value}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
                <tr>
                    <td><h3>${type.title}</h3></td>
                    <c:if test="${type == 'OBJECTIVE'}">
                        <td>
                            <h3><%=((TextSection) section).getContent()%>
                            </h3>
                        </td>
                    </c:if>
                </tr>
                <c:if test="${type != 'OBJECTIVE'}">
                    <c:choose>
                        <c:when test="${type == 'PERSONAL'}">
                            <tr>
                                <td>
                                    <%=((TextSection) section).getContent()%>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATION'}">
                            <tr>
                                <td>
                                    <ul>
                                        <c:forEach var="item" items="<%=((ListSection) section).getStrings()%>">
                                            <li>${item}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                            <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty org.url}">
                                                <h3>${org.title}</h3>
                                            </c:when>
                                            <c:otherwise>
                                                <h3><a href="${org.url}">${org.title}</a></h3>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <c:forEach var="period" items="${org.periods()}">
                                    <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Period"/>
                                    <tr>
                                        <td><%=HtmlUtil.formatDates(period)%>
                                        </td>
                                        <td><b>${period.title}</b><br>${period.description}</td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:if>
            </c:forEach>
        </table>
        <button onclick="window.history.back()">Back</button>
    </div>
</div>
</body>
</html>