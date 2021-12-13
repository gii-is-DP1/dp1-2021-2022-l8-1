<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:playerProfileLayout pageName="profile"> 

    <span id="left-section">
        <div id="profile-details">
            <h3><c:out value="${player.firstName}"/></h3>
            <h3><c:out value="${player.surname}"/></h3>
        </div>

        <button id="my-rooms-btn"
                class="btn btn-default"
                onclick="location.href = '/players/profile/${player.id}/rooms/created';">
            Rooms
        </button>
        <button id="social-btn"
                class="btn btn-default"
                onclick="location.href = '/players/profile/${player.id}/social';">
            Social
        </button>
        <button id="stats-btn" 
                class="btn btn-default"
                onclick="location.href = '/players/profile/${player.id}/statistics';">
            More statistics
        </button>
        <button id="edit-prof-btn" 
                class="btn btn-default"
                onclick="location.href = '/players/profile/${player.id}/achievements';">
            Achievements
        </button>
        <button id="edit-prof-btn" 
                class="btn btn-default"
                onclick="location.href = '/players/edit/${player.id}';">
            Edit profile
        </button>

    </span>
    <span id="right-section">
        <div id="statistics-resume">
            <table>
                <tr>
                    <th class="table-title">Total Games</th>
                    
                </tr>
                <tr>
                    <th class="table-title">Time Played</th>
                  
                </tr>
                <tr>
                    <th class="table-title">Total Points</th>
                    
                </tr>
                <tr>
                    <th class="table-title">Favorite Island</th>
                    
                </tr>
                <tr>
                    <th class="table-title">Favorite Treasure</th>
                    
                </tr>
            </table>
        </div>
    </span>

</sevenislands:playerProfileLayout>