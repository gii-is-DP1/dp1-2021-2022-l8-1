<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<sevenislands:gameLayout title="Lobby" pageName="Lobby">

    <jsp:body>

        <div id="center-section">
        <form:form modelAttribute="game" class="form-horizontal" action="#">
            <div class="form-group has-feedback">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="row text-center">
                            <p><strong><u>Game details</strong></u></p>
                            <br>
                            <br>
                            <p>Name: <c:out value="${game.name}"/></p>
                            <p>Room code: <c:out value="${game.code}"/></p>
                        </div>
                    </div> 
                    <div class="col-sm-4">
                        <div class="row text-center">
                            <p><strong><u>Friends</strong></u></p>
                            <br>
                            <br>
                            
                        </div>

                    </div>
                    <div class="col-sm-4">
                        <div class="row text-center"><strong><u>Party members</strong></u></div>
                        <br>
                        <br>
                        <c:forEach items ="${game.players}" var="p">
                            <div class="row text-center">
                                <c:out value = "${p.user.username}"/><br>
                            </div>
                        </c:forEach> 
                    </div>
                    
                </div>
                <br><br><br><br>
                <div class="row text-center">
                    <c:choose>
                        <c:when test="${game.player.id==player.id}">
                            <a href="/games/delete/${game.id}" class="btn btn-default">Cancel</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <c:if test="${totalplayers>1}">
                                <a href="/boards/${game.code}/init" class="btn btn-default">Start match</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <a href="/games/exit/${game.id}" class="btn btn-default">Exit</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        </div>
    </jsp:body>

    


</sevenislands:gameLayout>