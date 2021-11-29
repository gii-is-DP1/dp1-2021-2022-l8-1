<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:playerProfileLayout pageName="achievement" subtitle="Achievements">

    <div class="achievements-container">
        <c:forEach items="${achieved}" var="achievement">
        
        <div class="achievement">
            <img src="${achievement.icon}" alt="${achievement.name}" style="width:100px;height:100px;">
        
            <span class="text-center"><c:out value="${achievement.name}"/></span>
        </div>
        
     
        

        </c:forEach>

        <c:forEach items="${notAchieved}" var="achievement">                 
            
            <div class="achievement non-acquired">
                <img src="${achievement.icon}" alt="${achievement.name}" style="width:100px;height:100px;">
            
                <span class="text-center"><c:out value="${achievement.name}"/></span>
            </div>
            
        </c:forEach>
    </div>
            
    
            



</sevenislands:playerProfileLayout>

