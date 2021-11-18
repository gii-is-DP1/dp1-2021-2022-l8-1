<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>


<SevenIslands:layout pageName="Players">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Player</h2>
        <form:form modelAttribute="player" class="form-horizontal" action="/players/playerAdmins/save">
            <div class="form-group has-feedback">
                <SevenIslands:inputField label="FirstName" name="firstName"/>
                <SevenIslands:inputField label="Surname" name="surname"/>
                <SevenIslands:inputField label="Email" name="email"/>
                <!--<SevenIslands:inputField label="Username" name="user"/>-->
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="playerId" value="${player.id}"/>
                    <button class="btn btn-default" type="submit">Save Player</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</SevenIslands:layout>
