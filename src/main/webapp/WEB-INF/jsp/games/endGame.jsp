<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:simpleLayout pageName="endGame" title="End Game">   

    <div id="row center-section">

        <div id="players">

            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Position</th>
                    <th>Username</th>
                    <th>Points</th>
                </tr>
                </thead>
                <tbody>
            
                <c:set var="count" value="1" scope="page" />

                <c:forEach items="${pointsOfPlayer}" var="map">

                    <tr>
                        <td>
                            <c:out value="${count}"/>
                        </td>
                        <td>
                            <c:out value="${map.key.user.username}"/>
                        </td>
                        
                        <td>
                            <c:out value="${map.value}"/>
                        </td>
                        
                    </tr>

                    <c:set var="count" value="${count + 1}" scope="page"/>

                </c:forEach>
                </tbody>
            </table>

        </div>

    </div>

    <div class="row text-center">
        <a href="/welcome" class="btn btn-default">Exit game</a>
    </div>

</sevenislands:simpleLayout>
