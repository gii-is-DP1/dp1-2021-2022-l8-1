<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<SevenIslands:layout pageName="board">
    
    <h2><c:out value="${now}"/></h2>

    <div class="row">
        <div class="col-md-12">
            <SevenIslands:board board="${board}"/>
            <!-- <c:forEach items="${chessBoard.pieces}" var="piece">
            	<game:piece size="100" piece="${piece}"/>
            	
            </c:forEach>  -->
        </div>
    </div>
</SevenIslands:layout>