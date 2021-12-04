<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="parameter" required="true"%>
<%@ attribute name="players" required="false" rtexprvalue="true" type="java.util.ArrayList" %>

<div class="ranking-table">


    <div class="table-header">
        <p style="width: 23%"></p>
        <p style="width: 40%">Player</p>
        <p style="width: 33%">${parameter}</p>
    </div>

    <div class="table-body">
        <table>
            <tbody>
                <c:forEach items="${players}" var="player">
                <tr>
                    <td class="pos-col"> <c:out value = "${players.indexOf(player) + 1}"/> </td>
                    <td> <a href="/players/profile/${player.id}" htmlEscape="true" /> <c:out value = "${player.user.username}"/> </a> </td>
                    <td> 
                        <c:if test="${parameter == 'Wins'}">
                            <c:out value = "${player.totalWins}"/>
                        </c:if>

                        <c:if test="${parameter == 'Points'}">
                            <c:out value = "${player.totalPointsAllGames}"/>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>


</div>
