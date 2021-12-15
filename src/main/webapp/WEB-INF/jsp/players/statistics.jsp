<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:playerProfileLayout pageName="statistics" subtitle="Statistics">

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
                            <th class="table-title">Time Played</th>
                            <th><c:out value="${maxTime}"></c:out> s</th>
                            <th><c:out value="${minTime}"></c:out> s</th>
                            <th><c:out value="${avgTime}"></c:out> s</th>
                            <th><c:out value="${totalTime}"></c:out> s</th>
                        </tr>
                        <tr>
                            <th class="table-title">Points</th>
                            <th><c:out value="${maxPoints}"></c:out></th>
                            <th><c:out value="${minPoints}"></c:out></th>
                            <th><c:out value="${avgPoints}"></c:out></th>
                            <th><c:out value="${totalPoints}"></c:out></th>
                        </tr>
                        <tr>
                            <th class="table-title">Games</th>
                            <th>-</th>
                            <th>-</th>
                            <th>-</th>
                            <th><c:out value="${totalGames}"></c:out></th>
                        </tr>
                        </table>
                    
                    
                </div>
            </span>
        </div>

</sevenislands:playerProfileLayout>