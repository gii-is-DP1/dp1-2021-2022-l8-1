<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="achievement">
    <h2>MyAchievements</h2>

    <table id="archievementTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Logros</th>
           
        </tr>
        </thead>

        <tbody>
        
        <td>
            <c:out value="CONSEGUIDOS"></c:out>   
            <c:forEach items="${conseguidos}" var="a">
                
                <tr>
                    <td>
                    <c:out value="${a.name}"/>
                    </td>         
                
                </tr>
            </c:forEach>
            <tr></tr>
        </td> 
        
        <td>
            <c:out value="NO CONSEGUIDOS"></c:out>  
            <c:forEach items="${noc}" var="b">
                <tr>
                    <td>
                    <c:out value="${b.name}"/>
                    </td>         
                </tr>
            </c:forEach>
        </td>
        
        </tbody>
    </table>
</SevenIslands:layout>