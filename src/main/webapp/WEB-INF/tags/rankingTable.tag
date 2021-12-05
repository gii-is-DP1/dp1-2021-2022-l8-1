<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="parameter" required="true"%>
<%@ attribute name="playersWithStats" required="false" rtexprvalue="true" type="java.util.ArrayList" %>

<div class="ranking-table">


    <div class="table-header">
        <p style="width: 23%"></p>
        <p style="width: 40%">Player</p>
        <p style="width: 33%">${parameter}</p>
    </div>

    <div class="table-body">
        <table>
            <tbody>
                <c:forEach items="${playersWithStats}" var="playerWithStat">
                <tr>
                    <td class="pos-col"> <c:out value = "${playerWithStat.players.indexOf(player) + 1}"/> </td>
                    <td> <a href="/players/profile/${playerWithStat.player.id}" htmlEscape="true" /> <c:out value = "${playerWithStat.player.user.username}"/> </a> </td>
                    <td> 
                        <c:if test="${parameter == 'Wins'}">
                            <c:out value = "${playerWithStat.player}"/>
                        </c:if>

                        <c:if test="${parameter == 'Points'}">
                            <c:out value = "${playerWithStat.player}"/>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>


</div>
