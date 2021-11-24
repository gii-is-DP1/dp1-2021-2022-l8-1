<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:layout pageName="admins">
    <h2>
        <c:if test="${admin['new']}">New </c:if> Admin
    </h2>
    <form:form modelAttribute="admin" class="form-horizontal" id="add-admin-form">
        <div class="form-group has-feedback">
            <sevenislands:inputField label="First Name" name="firstName"/>
            <sevenislands:inputField label="Surname" name="surname"/>
            <sevenislands:inputField label="Password" name="user.password"/>
            <sevenislands:inputField label="Email" name="email"/>
            <sevenislands:inputField label="Username" name="user.username"/>
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
</sevenislands:layout>
