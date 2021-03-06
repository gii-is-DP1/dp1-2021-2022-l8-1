<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:layout pageName="games">
    <h2>Games</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Games</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;">Players</th>
            <th style="width: 200px;">Watch Game</th>
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
                    <c:forEach  items="${game.players}" var="p"> 
                        <c:out value="${p.user.username}"/> 
                    </c:forEach>
                </td>
                <td>
                    <button class="btn btn-danger" type="">Watch</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</sevenislands:layout>
