<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:profileLayout pageName="rooms" subtitle="Rooms">

    <div id="mid-section">
        <div id="table-options">
            <select class="selectpicker" id="typeRoom">
                <option value="">Created </option>
                <option value="playedByMe">Played</option>
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
                            <c:forEach items ="${game.players}" var="p">
                            
                            <c:out value = "${p.user.username}"/>  <!--AQUI SE PUEDE PONER LO DE DIRECCIONAR A LA VISTA DE UN PERFIL DANDOLE EL ID-->
                            
                
                            </c:forEach>  
                            </td>  
                        
                            
                        </tr>
                    </c:forEach>
        
        
                </tbody>
            </table>
        </div>
    </div>

    <script type="text/javascript">
        // FIXME: Hay que modificar el metodo para que te redirija a la pagina correcta

        function doSearch(){
            window.
            location="/games/"+document.getElementById("typeRoom").value;
        }
    </script>

</sevenislands:profileLayout>
