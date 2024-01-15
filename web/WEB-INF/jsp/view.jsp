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
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
            <%=sectionEntry.getKey().toHtml(sectionEntry.getKey(), sectionEntry.getValue())%><br>
        </c:forEach>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>