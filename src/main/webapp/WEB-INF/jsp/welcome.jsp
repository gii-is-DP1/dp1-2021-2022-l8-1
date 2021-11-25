<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<sevenislands:simpleLayout pageName="home" title="Welcome">

    <div id="mid-section">

        <span id="left-section">
            <h2>Project ${title}</h2>
            <p><h2>Group ${group}</h2></p>
            <p><ul>
                <c:forEach items="${persons}" var="person">
                    <li>
                        <c:out value="${person.firstName} ${person.surname}" />
                    </li>
                </c:forEach>
            </ul></p>
        </span>

        <span id="right-section">
            <div id="welcome-logo">
                <spring:url value="/resources/images/logoUS.png" htmlEscape="true" var="usLogo"/>
                <img class="img-responsive" src="${usLogo}"/>
            </div>
        </span>

    </div>

</sevenislands:simpleLayout>
