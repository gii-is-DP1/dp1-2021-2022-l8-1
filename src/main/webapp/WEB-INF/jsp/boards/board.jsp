<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>



<sevenislands:layout pageName="board">
    
    
    <div id="top-section">
        <img id="profile-avatar" src="/resources/images/profile-photo.png">
        <h2><c:out value="${game.name}"/></h2>
    </div>
    <!--<p> Your will lose your turn in <span id="countdowntimer"> 10 </span> Seconds</p>-->
    <c:out value="${tempo}"></c:out>
    <div>
        <div class="col-md-4">
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
    
    <h2><c:out value="${game.valueOfDie}"/></h2>


    
   
</sevenislands:layout>