<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="player" required="false" rtexprvalue="true" type="org.springframework.samples.SevenIslands.player.Player" %>
<%@ attribute name="active" required="false" rtexprvalue="true"%>
<%@ attribute name="animated" required="false" rtexprvalue="true"%>


<li class="player-item ${active == true ? 'player-item-active' : ''} ${animated == true ? '.player-item-animated' : ''}">
    <a href="/players/profile/${player.id}" htmlEscape="true">

        <p class="username">${player.user.username}</p>
        <img 
            class="profile-photo" 
            src="${player.profilePhoto == null ? '/resources/images/profile-photo.png' : player.profilePhoto}" 
            alt="Foto de perfil"
        >
    
    </a>
</li>