<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <jsp:body>
        <h2>
            <c:if test="${game['new']}">New </c:if> Game
        </h2>
        <form:form modelAttribute="game"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${game.id}"/>
            <input type="hidden" name="actualPlayer" value="${game.actualPlayer}"/>
            <input type="hidden" name="numberOfPlayers" value="${game.numberOfPlayers}"/>
            <input type="hidden" name="cartsRemain" value="${game.remainsCards}"/>
            <input type="hidden" name="numberOfTurn" value="${game.numberOfTurn}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Game</label>
                    <div class="col-sm-10">
                        <c:out value="Name: ${game.name} - Room code: ${game.code} - Jugador Actual: ${game.actualPlayer}"/>
                    </div>
                </div>
                <petclinic:inputField label="Name" name="name"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${game['new']}">
                            <button class="btn btn-default" type="submit">Add Game</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Game</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!game['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
