<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:playerProfileLayout pageName="rooms" subtitle="Rooms">

    <div id="center-section">
        <div id="table-options">
            <select class="selectpicker" id="typeRoom">
                <option value="created">Created</option>
                <option value="played" selected="selected">Played</option>
            </select>
            <input type="button" value="Search" onclick="doSearch()" />
        </div>
    
        <div id="games">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Games</th>
                    <th>Date</th>
                    <th>Players</th>
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
                                <c:forEach items ="${game.players}" var="player">
                                    <a href="/players/profile/${player.id}">
                                        <c:out value = "${player.user.username}"/>
                                    </a>
                                </c:forEach>  
                            </td>
                        </tr>
                    </c:forEach>
        
        
                </tbody>
            </table>
        </div>
    </div>

    <script type="text/javascript">
        function doSearch(){
            optionValue = document.getElementById("typeRoom").value;
            
            if(optionValue == "created") {
                window.location = "/players/profile/${player.id}/rooms/created";
            } else {
                window.location = "/players/profile/${player.id}/rooms/played";
            }
        }
    </script>

</sevenislands:playerProfileLayout>