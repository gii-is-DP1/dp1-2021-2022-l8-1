<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:adminProfileLayout pageName="admins">

    <span id="center-section">
        <div id="profile-details">
            <h3>Username: </h3><h4><c:out value="${admin.user.username}"/></h4>
            <h3>First Name: </h3><h4><c:out value="${admin.firstName}"/></h4>
            <h3>Surname: </h3><h4><c:out value="${admin.surname}"/></h4>
            <h3>Email: </h3><h4><c:out value="${admin.email}"/></h4>
        </div>
        <button class="btn btn-default" onclick="location.href='/admins/profile/edit/${admin.id}'">
            Edit profile
        </button>
    </span>

</sevenislands:adminProfileLayout>
