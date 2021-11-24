<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:layout pageName="achievement">
    <h2>My Achievements</h2>

    

    <div class="achievements-container">
        <c:forEach items="${conseguidos}" var="a">
        
        <div class="achievement">
            <img src="${a.icon}" alt="${a.name}" style="width:100px;height:100px;">
        
            <span class="text-center"><c:out value="${a.name}"/></span>
        </div>
        
     
        

        </c:forEach>

        <c:forEach items="${noc}" var="b">                 
            
            <div class="achievement non-acquired">
                <img src="${b.icon}" alt="${b.name}" style="width:100px;height:100px;">
            
                <span class="text-center"><c:out value="${b.name}"/></span>
            </div>
            
        </c:forEach>
    </div>
            
    
            



</sevenislands:layout>

