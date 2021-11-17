<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="admins">
    <h2>Admin Profile</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Games</th>
            <th style="width: 200px;">Date</th>
            <th>Players</th>
            <th>Delete game</th>
            <th>Update game</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${admins}" var="admin">
            <tr>
                <td>
                    <c:out value="${admin.user.username}"/>
                </td>
                <td>
                    <c:out value="${admin.firstName}"/>
                </td>
                <td>
                    <c:out value="${admin.surname}"/>
                </td>
                <td>
                    <c:out value="${admin.email}"/>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</SevenIslands:layout>
