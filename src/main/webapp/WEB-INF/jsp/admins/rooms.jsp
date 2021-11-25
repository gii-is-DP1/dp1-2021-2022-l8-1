<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:adminLayout pageName="games" subtitle="games">

    <div id="games">

        <table class="table table-striped">
            <thead>
            <tr>
                <th>Games</th>
                <th>Date</th>
                <th>Players</th>
                <th>Delete game</th>
            
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${games}" var="game">
                <tr>
                    <td>
                        <c:out value="${game.name}"/>
                    </td>
                    <td>
                        <c:out value="${game.startTime}"/>
                    </td>
                    <td>
                        <c:forEach  items="${game.players}" var="player"> 
                            <a href="/players/profile/${player.id}">
                                <c:out value = "${player.user.username}"/>
                            </a>
                        </c:forEach>
                    </td>
                    <td>
                        <spring:url value="/games/delete/{gameId}" var="gameUrl">
                            <spring:param name="gameId" value="${game.id}"/>
                        </spring:url>
                        <a class="btn btn-danger" href="${fn:escapeXml(gameUrl)}">Delete</a>
                    </td>
                    
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

</sevenislands:adminLayout>
