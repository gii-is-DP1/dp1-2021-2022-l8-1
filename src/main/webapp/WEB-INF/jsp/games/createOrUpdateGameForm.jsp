<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="games">
    <jsp:attribute name="customScript">
        <script type="text/javascript">
            var result           = '';
                var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
                var charactersLength = characters.length;
                for ( var i = 0; i < 9; i++ ) {
                result += characters.charAt(Math.floor(Math.random() * charactersLength));
                }

            window.onload = function () {
                document.getElementById("roomCode").value = result;
            }
        </script>
    </jsp:attribute>

    <jsp:body>
        <h2>
            <c:if test="${game['new']}">New </c:if> Game
        </h2>
        <form:form modelAttribute="game"
                   class="form-horizontal" action="/games/save">
            <input type="hidden" name="id" value="${game.id}"/>
            <input type="hidden" name="actualPlayer" value="${game.actualPlayer}"/>
            <input type="hidden" name="numberOfPlayers" value="${game.numberOfPlayers}"/>
            <input type="hidden" name="cartsRemain" value="${game.remainsCards}"/>
            <input type="hidden" name="numberOfTurn" value="${game.numberOfTurn}"/>
            <div class="form-group has-feedback">
                <c:if test="${not game['new']}">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Game</label>
                        <div class="col-sm-10">
                            <c:out value="Name: ${game.name} - Room code: ${game.code} - Jugador Actual: ${game.actualPlayer}"/>
                        </div>
                    </div>
                </c:if>
                <SevenIslands:inputField label="Name" name="name"/>
                <c:if test="${game['new']}">
                    <input type="hidden" name="code" id="roomCode"/>
                    <input type="hidden" name="gameId" value="${game.id}"/>
                </c:if>
                <div class="row">
                    <label class="col-sm-2 control-label"
                        for="privacity-input">Privacity
                    </label>
                    <div class="col-sm-10">
                        <select id="privacity-input" name="privacity" class="selectpicker">
                            <option value="PUBLIC">Public</option>
                            <option value="PRIVATE">Private</option>
                        </select>
                    </div>
                </div>
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
    </jsp:body>
</SevenIslands:layout>
