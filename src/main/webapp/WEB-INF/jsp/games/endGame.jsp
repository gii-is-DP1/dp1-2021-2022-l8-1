<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:gameLayout title="End Game" pageName="End Game"> 

    <span id="center-section" class="end-game-section">
                
        <c:set var="count" value="1" scope="page" />
        <c:forEach items="${playersByPunctuation}" var="map">

            <sevenislands:endGamePlayer 
                    player="${map.key}" 
                    punctuation="${map.value}"
                    position="${count}"/>

            <c:set var="count" value="${count + 1}" scope="page"/>

        </c:forEach>

    </span>
    
    <span id="extra-section">

        <a href="/welcome" class="btn btn-default">Exit game</a>
    
    </span>

</sevenislands:gameLayout>
