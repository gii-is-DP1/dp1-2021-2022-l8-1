<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:simpleLayout pageName="players" title="Players">

    <div id="center-section">

        <div id="table-options">
            <form>
                <label for="filterName">Find by username:</label>
                <input type="text" id="filterName" name="filterName">
                <input type="submit" value="Find"> 
            </form>
        </div>

        <div id="players">

            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Email</th>
                    <th>Username</th>
                    <th>Delete player</th>
                    <th>Edit player</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="filterName" value="${filterName}"></c:set>
                <c:forEach items="${players}" var="player" begin="${begin}" end="${end}">

                        <tr>
                            <td>
                                <c:out value="${player.firstName}"/>
                            </td>
                            <td>
                                <c:out value="${player.surname}"/>
                            </td>
                            
                            <td>
                                <c:out value="${player.email}"/>
                            </td>

                            <td>
                                <c:out value="${player.user.username}"/>
                            </td>

                            <td>
                                <spring:url value="/players/delete/{playerId}" var="playerUrl">
                                    <spring:param name="playerId" value="${player.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(playerUrl)}" class="btn btn-danger">Delete</a>
                            </td>

                            <td>
                                <spring:url value="/players/edit/{playerId}" var="playerUrl">
                                    <spring:param name="playerId" value="${player.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(playerUrl)}" class="btn btn-warning">Edit</a>
                            </td>
                            
                        </tr>

                </c:forEach>

                
                </tbody>
            </table>

            <div class="row text-center">
                <nav aria-label="Page navigation example">
                    <ul class="pagination">
                    <li class="page-item"><a class="page-link" href="/players?begin=0&end=9&filterName=${filterName}">1</a></li>
                    <li class="page-item"><a class="page-link" href="/players?begin=10&end=19&filterName=${filterName}">2</a></li>
                    <li class="page-item"><a class="page-link" href="/players?begin=20&end=29&filterName=${filterName}">3</a></li>
                    </ul>
                </nav>
            </div>

        </div>

    </div>

</sevenislands:simpleLayout>
