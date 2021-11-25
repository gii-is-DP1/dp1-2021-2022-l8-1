<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<SevenIslands:layout pageName="error">
    <h2><fmt:message key="welcome"/></h2>
    

    <p>
    <h2><c:out value="${now}"/></h2>
    <div class="row">
        <h2>Project ${title}</h2>
        <p><h2>Group ${group}</h2></p>
        <p><ul>
            <c:forEach items="${persons}" var="person">
                <li>
                    <c:out value="${person.firstName} ${person.surname}" />
                </li>
            </c:forEach>
        </ul></p>
    </div>

    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/logoUS.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
    </div>
 
</SevenIslands:layout>