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
        
                <button id="my-games-btn" 
                        class="btn btn-default" 
                        onclick="location.href = '/games';"> 
                    My games
                </button>
                <button id="social-btn" 
                        class="btn btn-default"
                        onclick="location.href = '/social';">
                    Social
                </button>
                <button id="stats-btn" 
                        class="btn btn-default"
                        onclick="location.href = '/statistics';">
                    Statistics
                </button>
                <button id="edit-prof-btn" 
                        class="btn btn-default"
                        onclick="location.href = '/players/player/edit/${player.id}';">
                    Edit profile
                </button>
            </span>
            <span id="right-section">
                <div id="statistics-resume">
                    <table>
                        <tr>
                            <th><strong>Total Games</strong></th>
                            <th><c:out value="${player.totalGames}" /></th>
                        </tr>
                        <tr>
                            <th><strong>Time Played</strong></th>
                            <th><c:out value="${player.totalTimeGames}" /></th>
                        </tr>
                        <tr>
                            <th><strong>Total Points</strong></th>
                            <th><c:out value="${player.totalPointsAllGames}" /></th>
                        </tr>
                        <tr>
                            <th><strong>Favorite Island</strong></th>
                            <th><c:out value="${player.favoriteIsland}" /></th>
                        </tr>
                        <tr>
                            <th><strong>Favorite Treasure</strong></th>
                            <th><c:out value="${player.favoriteTreasure}" /></th>
                        </tr>
                    </table>
                </div>
            </span>
        </div>

</sevenislands:playerLayout>