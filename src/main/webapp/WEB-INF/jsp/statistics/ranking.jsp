<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:adminLayout pageName="ranking" subtitle="Ranking">

    <div id="ranking-winners">

        <h2>Winners</h2>

        <sevenislands:winner position="1" player="${players[0]}"/>
        <sevenislands:winner position="2" player="${players[1]}"/>
        <sevenislands:winner position="3" player="${players[2]}"/>

    </div>

    <div id="ranking-wins">

        <h2>Wins</h2>
        <sevenislands:rankingTable parameter="Wins" players=""/>

    </div>

    <div id="ranking-points">

        <h2>Points</h2>
        <sevenislands:rankingTable parameter="Points" players=""/>

    </div>

</sevenislands:adminLayout>