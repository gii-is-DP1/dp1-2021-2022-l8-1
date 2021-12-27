<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="player" required="false" rtexprvalue="true" type="org.springframework.samples.SevenIslands.player.Player" %>
<%@ attribute name="punctuation" required="false" rtexprvalue="true"%>
<%@ attribute name="position" required="false" rtexprvalue="true"%>


<div class="end-game-player ${'end-game-player-'.concat(position)}">
    <a href="/players/profile/${player.id}" htmlEscape="true">

        <h1 class="position"><c:out value="${position}"/></h1>

        <div class="player-details">
            <img 
            class="profile-photo" 
            src="${player.profilePhoto == null ? '/resources/images/profile-photo.png' : player.profilePhoto}" 
            alt="Foto de perfil"
            >
            <p class="username"><c:out value="${player.user.username}"/></p>
        </div>
        <p class="punctuation"><c:out value="${punctuation}"/> points</p>
    
    </a>
</div>