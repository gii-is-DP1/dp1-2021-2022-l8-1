<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="url" required="false" rtexprvalue="true" %>
<%@ attribute name="avatarBig" required="false" rtexprvalue="true" %>
<%@ attribute name="avatarSmall" required="false" rtexprvalue="true" %>

<div id="avatar" class="${avatarBig ? 'avatar-big': ''} ${avatarSmall ? 'avatar-small': ''}">
    <img class="img" src="/resources/images/profile-photo.png"/>
</div>