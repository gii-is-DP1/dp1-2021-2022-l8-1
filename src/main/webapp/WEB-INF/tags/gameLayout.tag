<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageName" required="true" %>
<%@ attribute name="customScript" required="false" fragment="true"%>
<%@ attribute name="title" required="false"%>
<%@ attribute name="subtitle" required="false"%>

<!doctype html>
<html>
<sevenislands:htmlHeader/>

<body>
<sevenislands:bodyHeader menuName="${pageName}"/>


<div id="body-section">
    <div id="top-section">
        <h2 id="title"><c:out value="${title}"></c:out></h2>
    </div>

    <c:if test="${not empty message}" >
        <div class="alert alert-${not empty messageType ? messageType : 'info'}" role="alert">
            <c:out value="${message}"></c:out>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button> 
        </div>
    </c:if>

    <div id="mid-section">
        <jsp:doBody/>
    </div>


    <div id="bottom-decoration">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="#0099ff" fill-opacity="1" d="M0,32L48,32C96,32,192,32,288,53.3C384,75,480,117,576,112C672,107,768,53,864,37.3C960,21,1056,43,1152,58.7C1248,75,1344,85,1392,90.7L1440,96L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>
    </div>
    <div id="bottom-section"></div>

</div>

<sevenislands:footer/>
<jsp:invoke fragment="customScript" />

</body>

</html>
