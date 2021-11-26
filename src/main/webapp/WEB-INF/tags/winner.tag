<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="position" required="true"%>
<%@ attribute name="player" required="false"%>

<div class="winner">

    <h2 id="position">
        ${position} ${position == 1 ? "st" : ""}${position == 2 ? "nd" : ""}${position == 3 ? "rd" : ""}
    </h2>

    <a href="" htmlEscape="true" /> <!-- /players/profile/{player.id} -->
        <img src="/resources/images/profile-photo.png" alt="Foto de perfil" style="width: 20px;"> <!-- {player.profilePhoto} -->
        <p>playerusername</p> <!-- {player.user.username} -->
    </a>

</div>
