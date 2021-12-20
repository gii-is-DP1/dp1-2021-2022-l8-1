<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<sevenislands:gameLayout title="Lobby" pageName="Lobby">

            <span id="left-section">

                <h2>Game details</h2>
                <div class="section-content">
                    <p><strong>Name: </strong> <c:out value="${game.name}"/></p>
                    <p><strong>Room Code: </strong><c:out value="${game.code}"/></p>
    
                    <c:choose>
                        <c:when test="${game.player.id==player.id}">
                            <a href="/games/delete/${game.id}" class="btn btn-default">Cancel</a>
                            <c:if test="${totalplayers>1}">
                                <a href="/boards/${game.code}/init" class="btn btn-default">Start match</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <a href="/games/exit/${game.id}" class="btn btn-default">Exit</a>
                        </c:otherwise>
                    </c:choose>
                </div>

            </span>
            
            <span id="center-section">
                <h2>Party members</h2>

                <div class="section-content">
                    <c:forEach items ="${game.players}" var="p">
                        <p><c:out value = "${p.user.username}"/></p>
                    </c:forEach>
                </div>

            </span>

            <span id="right-section">
                <h2>Friends</h2>
                <div class="section-content">

                </div>
            </span>

</sevenislands:gameLayout>