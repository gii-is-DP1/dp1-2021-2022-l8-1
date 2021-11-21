<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="achievement">
    <h2>My Achievements</h2>

    <table id="acquiredAchievementTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Acquired</th>
            <th style="width: 200px;">Description</th>
           
        </tr>
        </thead>

        <tbody>
            <c:forEach items="${conseguidos}" var="a">
                
                <tr>
                    <td>
                    <c:out value="${a.name}"/>
                    </td>     
                    
                    <td>
                    <c:out value="${a.description}"/>
                    </td>
                

                </tr>

            </c:forEach>

        </tbody>
    </table>

    <table id="nonAcquiredAchievementTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Non Acquired</th>
            <th style="width: 200px;">Description</th>
           
        </tr>
        </thead>

        <tbody>
            <c:forEach items="${noc}" var="b">
                
                <tr>
                    <td>
                    <c:out value="${b.name}"/>
                    </td>     
                    
                    <td>
                    <c:out value="${b.description}"/>
                    </td>
    
                </tr>

            </c:forEach>

        </tbody>
    </table>
</SevenIslands:layout>

