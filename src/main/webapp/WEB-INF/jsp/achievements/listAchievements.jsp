<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<sevenislands:layout pageName="achievements">
    <h2>Achievements</h2>

    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 150px;">Description</th>
            <th>Edit Achievement</th>
            <th>Delete Achievement</th>
        </tr>
        </thead>
        <tbody>
            
        <c:forEach items="${achievements}" var="achievement">
            <tr>

                <td>
                    <c:out value="${achievement.name}"/>
                </td>

                <td>
                    <c:out value="${achievement.description}"/>
                </td>

                <td>
                    <spring:url value="/achievements/edit/{achievementId}" var="achievementUrl">
                        <spring:param name="achievementId" value="${achievement.id}"/>
                    </spring:url>
                    <a class="btn btn-warning" href="${fn:escapeXml(achievementUrl)}">Edit</a>
                </td>

                <td>
                    <spring:url value="/achievements/delete/{achievementId}" var="achievementUrl">
                        <spring:param name="achievementId" value="${achievement.id}"/>
                    </spring:url>
                    <a class="btn btn-danger" href="${fn:escapeXml(achievementUrl)}">Delete</a>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</sevenislands:layout>