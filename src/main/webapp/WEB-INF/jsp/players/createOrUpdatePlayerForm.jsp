<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:layout pageName="players">
    <h2>
        <c:if test="${player['new']}">New </c:if> Player
    </h2>
    <form:form modelAttribute="player" class="form-horizontal" id="add-player-form">
        <div class="form-group has-feedback">
            <SevenIslands:inputField label="Profile Photo" name="profilePhoto"/>
            <SevenIslands:inputField label="First Name" name="firstName"/>
            <SevenIslands:inputField label="Surname" name="surname"/>
            <SevenIslands:inputField label="Email" name="email"/>
            <SevenIslands:inputField label="Username" name="user.username"/>
            <SevenIslands:inputField label="Password" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${player['new']}">
                        <button class="btn btn-default" type="submit">Add Player</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Player</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</sevenislands:layout>
