<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:layout pageName="games">

    <script type="text/javascript">
        function doSearch(){
            window.
            location="/games/"+document.getElementById("typeRoom").value;
        }
    </script>

    <h2><c:out value="${titletext}"/></h2>    

    <br>
    <br>
    <div>
        <select class="selectpicker" id="typeRoom">
            <option value="">Rooms created by me </option>
            <option value="playedByMe">Rooms where I played</option>
        </select>          
        <input type="button" value="Search" onclick="doSearch()" />
    </div>
    <br>
    <br>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Games</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;">Players</th>
           
        </tr>
        </thead>
        <tbody>
            
        <c:forEach items="${games}" var="game">
                <tr>
                    <td>
                        <c:out value="${game.name}"/>
                    </td>
    
                    <td>
                        <c:out value="${game.startTime}"/>
                    </td> 
    
                    <td>
                    <c:forEach items ="${game.players}" var="p">
                    
                    <c:out value = "${p.user.username}"/>  <!--AQUI SE PUEDE PONER LO DE DIRECCIONAR A LA VISTA DE UN PERFIL DANDOLE EL ID-->
                    
        
                    </c:forEach>  
                    </td>  
                
                    
                </tr>
            </c:forEach>


        </tbody>
    </table>

</sevenislands:layout>
