<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>



<sevenislands:layout pageName="board">
    
    
    <div id="top-section">
        <img id="profile-avatar" src="/resources/images/profile-photo.png">
        <h2><c:out value="${game.name}"/></h2>
    </div>
    <p> Your will lose your turn in <span id="countdowntimer"> 10 </span> Seconds</p>
    <div>
        <div class="col-md-4">
            <div class="playersList">
                <h3 class="text-center">Player List:</h3>
                <c:forEach items ="${game.players}" var="p">
                    <div class="row text-center">
                        <c:out value = "${p.user.username}"/><br>
                    </div>
                </c:forEach> 
            </div>
        </div>
        <div class="col-md-8 playersList">
            <sevenislands:board board="${board}"/>
            <!-- <c:forEach items="${board.cells}" var="cell">
            	<game:cell size="100" cell="${cell}"/>
            </c:forEach>  -->
        </div>
    </div>

    <!-- DICE -->
    <script>
        var face0=new Image()
        face0.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9ce32f-dado1.png"
        var face1=new Image()
        face1.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9d15f6-dado2.png"
        var face2=new Image()
        face2.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9d48bd-dado3.png"
        var face3=new Image()
        face3.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9d7bc5-dado4.png"
        var face4=new Image()
        face4.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9daa7f-dado5.png"
        var face5=new Image()
        face5.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9ddd30-dado6.png"
    </script>
 
 
    <img src="" name="mydice">
 
    <form>
    <input type="button" value="Lanza dado" onClick="lanzar()">
    </form>
 
    <script>
    function lanzar()
    {
        var randomdice=Math.round(Math.random()*5);
        document.images["mydice"].src=eval("face"+randomdice+".src");
    }
    </script>
</sevenislands:layout>