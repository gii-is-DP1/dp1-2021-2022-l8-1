<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>


<SevenIslands:layout pageName="Lobby">
    <jsp:body>

        <div class="row text-center"><h2>Lobby</h2></div>
        <br>
        <br>

        <form:form modelAttribute="game" class="form-horizontal" action="#">
            <div class="form-group has-feedback">
                <div class="row text-md-left">
                    <div class="col-md">
                        <div class="row text-center">
                            <a href="/games/${game.id}/lobby" class="btn btn-warning">Refresh room</a>
                        </div>
                    </div>
                    <br>
                    <br> 
                </div>
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

                    <a href="/games/delete/${game.id}" class="btn btn-default">Cancel</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="/boards/${game.code}" class="btn btn-default">Start match</a>

                </div>
            </div>
        </form:form>
    </jsp:body>

</SevenIslands:layout>