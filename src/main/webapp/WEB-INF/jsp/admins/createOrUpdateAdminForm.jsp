<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="admins">
    <h2>
        <c:if test="${admin['new']}">New </c:if> Admin
    </h2>
    <form:form modelAttribute="admin" class="form-horizontal" id="add-admin-form">
        <div class="form-group has-feedback">
            <SevenIslands:inputField label="First Name" name="firstName"/>
            <SevenIslands:inputField label="Surname" name="surname"/>
            <SevenIslands:inputField label="Password" name="user.password"/>
            <SevenIslands:inputField label="Email" name="email"/>
            <SevenIslands:inputField label="Username" name="user.username"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${admin['new']}">
                        <button class="btn btn-default" type="submit">Add Admin</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Admin</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</SevenIslands:layout>
