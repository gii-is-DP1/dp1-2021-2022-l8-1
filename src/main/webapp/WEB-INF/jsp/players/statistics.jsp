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
                            <th class="table-title">Max <span class="glyphicon glyphicon-circle-arrow-up"></span> </th>
                            <th class="table-title">Min <span class="glyphicon glyphicon-circle-arrow-down"></span> </th>
                            <th class="table-title">Average <span class="glyphicon glyphicon-record"></span> </th>
                            <th class="table-title">Total <span class="glyphicon glyphicon glyphicon-plus-sign"></span> </th>
                        </tr>
                        <tr>
                            <th class="table-title">Time Played</th>
                            <sevenislands:statisticTableCell value="${maxTime}" isTime="true"/>
                            <sevenislands:statisticTableCell value="${minTime}" isTime="true"/>
                            <sevenislands:statisticTableCell value="${avgTime}" isTime="true"/>
                            <sevenislands:statisticTableCell value="${totalTime}" isTime="true"/>
                        </tr>
                        <tr>
                            <th class="table-title">Points</th>
                            <sevenislands:statisticTableCell value="${maxPoints}"/>
                            <sevenislands:statisticTableCell value="${minPoints}"/>
                            <sevenislands:statisticTableCell value="${avgPoints}"/>
                            <sevenislands:statisticTableCell value="${totalPoints}"/>
                        </tr>
                        <tr>
                            <th class="table-title">Games</th>
                            <th>-</th>
                            <th>-</th>
                            <th>-</th>
                            <sevenislands:statisticTableCell value="${totalGames}"/>
                        </tr>
                        </table>
                    
                    
                </div>
            </span>
        </div>

</sevenislands:playerProfileLayout>