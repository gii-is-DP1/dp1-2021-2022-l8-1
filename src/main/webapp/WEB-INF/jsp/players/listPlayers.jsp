<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:layout pageName="players">

    <h2>Players</h2>
    <br>
    <br>
    <form>
        <label for="filterName">Find by username:</label>
        <input type="text" id="filterName" name="filterName"><br><br>
        <input type="submit" value="Find">
    </form>
    <br>
    <br>
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
        <c:set var="filterName" value="${filterName}"></c:set>
        <c:forEach items="${players}" var="player">
            <c:if test="${fn:contains(player.user.username, filterName)}">
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
                    
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</sevenislands:layout>