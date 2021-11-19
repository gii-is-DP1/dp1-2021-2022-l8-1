<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:playerLayout pageName="moreStadistics">
    
        <div id="top-section">
            <img id="profile-avatar" src="/resources/images/profile-photo.png">
            <h2 id="title">
                <c:out value="${player.user.username}"/>
            </h2>
            <h2 id="sub-title">More Statistics</h2>
        </div>
        <div id="mid-section">
            <span id="center-section">
                <div id="statistics-full">
                    <table>
                        <tr>
                            <th></th>
                            <th class="table-title">Max</th>
                            <th class="table-title">Min</th>
                            <th class="table-title">Average</th>
                            <th class="table-title">Total</th>
                        </tr>
                        <tr>
                            <th class="table-title">Games</th>
                            <th>-</th>
                            <th>-</th>
                            <th>-</th>
                            <th><c:out value="${player.totalGames}"></c:out></th>
                        </tr>
                        <tr>
                            <th class="table-title">Time Played</th>
                            <th><c:out value="${player.maxTimeGame}"></c:out> s</th>
                            <th><c:out value="${player.minTimeGame}"></c:out> s</th>
                            <th><c:out value="${player.avgTimeGames}"></c:out> s</th>
                            <th><c:out value="${player.totalTimeGames}"></c:out> s</th>
                        </tr>
                        <tr>
                            <th class="table-title">Total Points</th>
                            <th><c:out value="${player.maxPointsOfGames}"></c:out></th>
                            <th><c:out value="${player.minPointsOfGames}"></c:out></th>
                            <th><c:out value="${player.avgTotalPoints}"></c:out></th>
                            <th><c:out value="${player.totalPointsAllGames}"></c:out></th>
                        </tr>
                        </table>
                    
                    
                </div>
            </span>
        </div>

</sevenislands:playerLayout>