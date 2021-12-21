<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>
<%@ attribute name="players" required="false" rtexprvalue="true" type="org.hibernate.collection.internal.PersistentBag" %>

<ul class="player-list">

    <c:forEach items="${players}" var="player">
        <sevenislands:playerItem player="${player}"/>
    </c:forEach>

</ul>