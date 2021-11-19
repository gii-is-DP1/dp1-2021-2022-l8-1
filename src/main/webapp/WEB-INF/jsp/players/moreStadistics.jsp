<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:playerLayout pageName="moreStadistics">
    
        <div id="mid-section">
            <span id="right-section">
                <div id="statistics-resume">
                    <table style="height: 360px;">
                        <tbody>
                        <tr style="height: 23px;">
                        <td style="width: 219px; height: 23px;">&nbsp;</td>
                        <td style="width: 64px; height: 23px;">&nbsp;Max</td>
                        <td style="width: 71px; height: 23px;">Min</td>
                        <td style="width: 103px; height: 23px;">&nbsp;Average</td>
                        <td style="width: 108.141px; height: 23px;">&nbsp;Total</td>
                        </tr>
                        <tr style="height: 23.5px;">
                        <td style="width: 219px; height: 23.5px;">&nbsp;Games</td>
                        <td style="width: 64px; height: 23.5px;">&nbsp;-</td>
                        <td style="width: 71px; height: 23.5px;">&nbsp;-</td>
                        <td style="width: 103px; height: 23.5px;">&nbsp;-</td>
                        <td style="width: 108.141px; height: 23.5px;">&nbsp;<c:out value="${player.totalGames}"></c:out></td>
                        </tr>
                        <tr style="height: 43px;">
                        <td style="width: 219px; height: 43px;">&nbsp;Time Played</td>
                        <td style="width: 64px; height: 43px;">&nbsp;<c:out value="${player.maxTimeGame}"></c:out></td>
                        <td style="width: 71px; height: 43px;">&nbsp;<c:out value="${player.minTimeGame}"></c:out></td>
                        <td style="width: 103px; height: 43px;">&nbsp;<c:out value="${player.avgTimeGames}"></c:out></td>
                        <td style="width: 108.141px; height: 43px;">&nbsp;<c:out value="${player.totalTimeGames}"></c:out></td>
                        </tr>
                        <tr style="height: 43px;">
                        <td style="width: 219px; height: 43px;">&nbsp;Total Points</td>
                        <td style="width: 64px; height: 43px;">&nbsp;<c:out value="${player.maxPointsOfGames}"></c:out></td>
                        <td style="width: 71px; height: 43px;">&nbsp;<c:out value="${player.minPointsOfGames}"></c:out></td>
                        <td style="width: 103px; height: 43px;">&nbsp;<c:out value="${player.avgTotalPoints}"></c:out></td>
                        <td style="width: 108.141px; height: 43px;">&nbsp;<c:out value="${player.totalPointsAllGames}"></c:out></td>
                        </tr>
                        </tbody>
                        </table>
                    
                    
                </div>
            </span>
        </div>

</sevenislands:playerLayout>