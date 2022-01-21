<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>



<sevenislands:gameLayout pageName="Board" title="" subtitle="${'GAME: '.concat(game.name)}">

    <form:form action="/boards/${game.code}/travel">

    <!--<p> Your will lose your turn in <span id="countdowntimer"> 10 </span> Seconds</p>-->
    <div id="turn-section">
        <h3>Turn:     
            &nbsp
            <span id="turn-mins">00</span>
            :
            <span id="turn-secs">00</span>
        </h3>
        
    </div>
    
    <div id="central-section">

        <div id="players-section">
            <h2>Players</h2>
            <sevenislands:playerList
                    players="${game.players}"
                    activePlayerId="${id_playing}"/>
        </div>

        <div id="game-section">
            <div id="board-section">

                <sevenislands:board board="${board}"/>

            </div>

            <div id="actions-section">

                <sevenislands:gameButton type="travel" pending="${game.dieThrows && id_playing==id}"/>
                <sevenislands:gameButton type="skip" pending="${!game.dieThrows && id_playing==id}"/>
                <sevenislands:gameButton type="dice" pending="${!game.dieThrows && id_playing==id}"/>

                <div id="dice" class="dice-stop">
                    <img src="/resources/images/dice/Dice1.png" alt="Dice">
                </div>

                <c:if test="${game.dieThrows && id_playing==id}">
                <div>
                    <select id="island-input" name="island" class="selectpicker">
                        <c:forEach items = "${options}" var = "o">
                            <option value="${o}"><c:out value="Island ${o}"></c:out></option>
                        </c:forEach>
                    </select>
                </div>
                </c:if>
            </div>
        </div>

    </div>
    <div id="deck-section">
        <sevenislands:deck cards="${player.cards}" disabled="${!(game.dieThrows && id_playing==id)}"></sevenislands:deck>

        <c:forEach items="${game.players}" var="opponent">
        <c:if test="${opponent != player}">
            <sevenislands:deck 
                    cards="${opponent.cards}" 
                    disabled="true"
                    playerUsername="${player.user.username}">
            </sevenislands:deck>
        </c:if>
        </c:forEach>
    </div>
    <div id="extra-section">
        <a href="/boards/${game.code}/leaveGame" class="btn btn-default">Leave Game</a>
    </div>

    </form:form>

    <%-- SCRIPTS --%>
    <sevenislands:boardButtonsScript/>
    <sevenislands:boardDeckScript/>
    <sevenislands:boardDiceScript/>
    <sevenislands:boardChangeTurnScript/>
    <sevenislands:boardTimerScript/>    
   
</sevenislands:gameLayout>