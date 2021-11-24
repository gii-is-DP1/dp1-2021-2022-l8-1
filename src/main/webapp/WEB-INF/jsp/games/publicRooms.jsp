<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:layout pageName="games">
    <script type="text/javascript">
        function clearSearch() {
            location.reload();
        }
        function doSearch(){
            window.location="/games/rooms/"+document.getElementById("code").value;
        }

        //So it doesn't search when you hit enter, because enter produces an error url
        document.addEventListener('DOMContentLoaded', () => {
        document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
            if(e.keyCode == 13) {
            e.preventDefault();
            }
        }))
        });

    </script>
        
    <h2>Games</h2>

    <br>
    <br>
    <div>
        Room code: <input type="text" name="code" id="code" size="50" th:value="${code}" required />
        &nbsp;
        <input type="button" value="Search" onclick="doSearch()" />
        &nbsp;
        <input type="button" value="Clear" id="btnClear" onclick="clearSearch()" />
    </div>
    <br>
    <br>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Games</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;">Players</th>
            <th style="width: 200px;">Join Game</th>
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
                    <c:forEach  items="${game.players}" var="p"> 
                        <c:out value="${p.user.username}"/> 
                    </c:forEach>
                </td>
                <td>
                    <button class="btn btn-danger" type="">Join</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</sevenislands:layout>
