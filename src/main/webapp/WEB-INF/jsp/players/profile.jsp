<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:playerLayout pageName="profile">
    
        <div id="top-section">
            <img id="profile-avatar" src="/resources/images/profile-photo.png">
            <h2><c:out value="${player.user.username}"/></h2>
        </div>
        <div id="mid-section">
            <span id="left-section">
                <div id="profile-details">
                    <h3><c:out value="${player.firstName}"/></h3>
                    <h3><c:out value="${player.surname}"/></h3>
                </div>
        
                <button class="btn btn-default">My games</button>
                <button class="btn btn-default">Social</button>
                <button class="btn btn-default">Statistics</button>
                <button class="btn btn-default">Edit profile</button>
            </span>
            <span id="right-section">
                <div id="statistics-resume">
                    <table>
                        <tr>
                            <th>Total Games</th>
                            <th><c:out value="${player.totalGames}" /></th>
                        </tr>
                        <tr>
                            <th>Time Played</th>
                            <th><c:out value="${player.totalTimeGames}" /></th>
                        </tr>
                        <tr>
                            <th>Total Points</th>
                            <th><c:out value="${player.totalPointsAllGames}" /></th>
                        </tr>
                        <tr>
                            <th>Favorite Island</th>
                            <th><c:out value="${player.favoriteIsland}" /></th>
                        </tr>
                        <tr>
                            <th>Favorite Treasure</th>
                            <th><c:out value="${player.favoriteTreasure}" /></th>
                        </tr>
                    </table>
                </div>
            </span>
        </div>

</sevenislands:playerLayout>