<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="SevenIslandsCss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageName" required="true" %>
<%@ attribute name="customScript" required="false" fragment="true"%>
<%@ attribute name="subtitle" required="false"%>

<!doctype html>
<html>
<SevenIslandsCss:htmlHeader/>

<body>
<SevenIslandsCss:bodyHeader menuName="${pageName}"/>


<div id="body-section">
    <c:if test="${not empty message}" >
        <div class="alert alert-${not empty messageType ? messageType : 'info'}" role="alert">
            <c:out value="${message}"></c:out>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button> 
        </div>
    </c:if>

    <div id="top-section">
        <a href="/players/profile/${player.id}">
            <img id="profile-avatar" src="/resources/images/profile-photo.png">
        </a>
        <a href="/players/profile/${player.id}">
            <h2><c:out value="${player.user.username}"/></h2>
        </a>

        <h2 id="sub-title"><c:out value="${subtitle}"></c:out></h2>
    </div>

    <div id="mid-section">
        <jsp:doBody/>
    </div>

    <div id="bottom-section"></div>

</div>

<SevenIslandsCss:footer/>
<jsp:invoke fragment="customScript" />

</body>

</html>
