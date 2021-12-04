<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:adminLayout pageName="ranking" subtitle="Ranking">

    <div id="ranking">
        <span id="ranking-winners" class="ranking-section">

            <h2>Winners</h2>
    
            <div id="winners">
                <sevenislands:winner position="1" player="${playersWins[0]}"/> <!-- ${players[2]} -->
                <sevenislands:winner position="2" player="${playersWins[1]}"/>
                <sevenislands:winner position="3" player="${playersWins[2]}"/>
            </div>
    
        </span>
    
        <span id="ranking-wins" class="ranking-section">
    
            <h2>Wins</h2>
            <sevenislands:rankingTable parameter="Wins" players="${playersWins}"/>
    
        </span>
    
        <span id="ranking-points" class="ranking-section">
    
            <h2>Points</h2>
            <sevenislands:rankingTable parameter="Points" players="${playersPoints}"/>
    
        </span>
    </div>

    <script>
        console.log(`${playersWins}`);
    </script>

</sevenislands:adminLayout>