<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="players">
    <h2>Players</h2>

    <table id="playersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th>Username</th>
            <th>Delete player</th>
            <th>Edit player</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="player">
            <tr>
                <td>
                    <c:out value="${player.firstName}"/>
                </td>
                <td>
                    <c:out value="${player.surname}"/>
                </td>
                
                <td>
                    <c:out value="${player.email}"/>
                </td>

                <td>
                    <c:out value="${player.user.username}"/>
                </td>

                <td>
                    <spring:url value="/players/delete/{playerId}" var="playerUrl">
                        <spring:param name="playerId" value="${player.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(playerUrl)}" class="btn btn-danger">Delete</a>
                </td>

                <td>
                    <spring:url value="/players/edit/{playerId}" var="playerUrl">
                        <spring:param name="playerId" value="${player.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(playerUrl)}" class="btn btn-warning">Edit</a>
                </td>
                
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</SevenIslands:layout>