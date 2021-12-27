<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>



<sevenislands:gameLayout pageName="Board" title="" subtitle="${'GAME: '.concat(game.name)}">
    
    <!--<p> Your will lose your turn in <span id="countdowntimer"> 10 </span> Seconds</p>-->
    <div id="turn-section">
        <h3>Turn: 00:00</h3>
    </div>
    
    <div id="central-section">

        <div id="players-section">
            <h2>Players</h2>
            <sevenislands:playerList 
                    players="${game.players}"
                    activePlayerId="12"/>
        </div>
        <div id="game-section"></div>

    </div>
    <div id="deck-section">
        <sevenislands:deck cards="${player.cards}"></sevenislands:deck>
    </div>

    <!-- <c:out value="${tempo}"></c:out>
    <div>
        <div class="col-md-4">
            <div class="islandsList">
                <h3 class="text-center">Islands:</h3>
                <c:set var="count" value="1" scope="page" />
                <c:forEach items ="${islands}" var="i">
                    <div class="row text-center">
                        Island<c:out value = "${count}"/>:
                        <c:out value = "${i.card.cardType}"/><br>
                        <c:set var="count" value="${count + 1}" scope="page"/>
                    </div>
                </c:forEach> 
            </div>
            <div class="playersList">
                <h3 class="text-center">Order of Turns:</h3>
                <c:forEach items ="${game.players}" var="p">
                    <div class="row text-center">
                        <c:choose>
                            <c:when test="${id_playing==p.id}">
                                <span style="background-color: yellow;"><c:out value = "${p.user.username}"/></span><br>
                            </c:when>
                            <c:otherwise>
                                <c:out value = "${p.user.username}"/><br>
                            </c:otherwise>
                        </c:choose>
                        
                    </div>
                </c:forEach> 
            </div>
        </div>
        <div class="col-md-8 playersList">
            <sevenislands:board board="${board}"/>
           
        </div>
    </div>

    <div>

        <c:forEach items ="${game.players}" var="p">
            <div class="row text-center">
                <c:out value = "${p.user.username} has "/>
                <c:forEach items ="${p.cards}" var="c">
                    <c:out value = "${c.cardType}, "/>
                </c:forEach>
                <br><br><br>
                
            </div>
        </c:forEach>

    </div>
    
    <c:if test="${game.dieThrows}">
        <c:forEach items ="${game.players}" var="p">
            <c:if test = "${id_playing==id}">
                <c:if test = "${id_playing==p.id}">

                    <form:form class="form-horizontal" action="/boards/${game.code}/travel">
            
                    <c:forEach items ="${p.cards}" var="c">
                    
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="card[]" value="${c.id}" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                <c:out value = "${c.cardType}"/>
                            </label>
                        </div>

                    </c:forEach>

                    <div>
                        <select id="island-input" name="island" class="selectpicker">
                            <c:forEach items = "${options}" var = "o">
                                <option value="${o}"><c:out value="Island ${o}"></c:out></option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-success">Travel</button>
                
                    </form:form>
                
                </c:if>
            </c:if>
        </c:forEach>
    </c:if>


    
    <c:if test="${id_playing==id}">
     
        <a href="/boards/${game.id}/changeTurn" class="btn btn-default">Finish Turn</a>
        
        <c:if test="${game.dieThrows==false}">
        <a href ="/boards/${game.id}/rollDie" class="btn btn-default" id="roll">Roll Die</a>
        
        </c:if>

    </c:if>

    <a href="/boards/${game.code}/leaveGame" class="btn btn-default">Leave Game</a>
    
    <h2><c:out value="${game.valueOfDie}"/></h2> -->


    
   
</sevenislands:gameLayout>