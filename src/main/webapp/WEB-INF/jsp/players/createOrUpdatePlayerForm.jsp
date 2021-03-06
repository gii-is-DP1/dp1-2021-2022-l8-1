<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="players">
    <div id="background"></div>
    <c:if test="${errorMessage != null}">
        <div class="alert alert-danger" role="alert">
            <c:out value="${errorMessage}"></c:out>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
    </c:if>
    <h2>
        <c:if test="${player['new']}">New </c:if> Player
    </h2>
    <form:form modelAttribute="player" class="form-horizontal" id="add-player-form">

        <div class="form-group has-feedback">
            <input type="hidden" name="version" value="${player.version}"/>
            
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

</SevenIslands:layout>