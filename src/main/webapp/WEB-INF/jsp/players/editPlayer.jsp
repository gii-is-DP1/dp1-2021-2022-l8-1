<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<sevenislands:layout pageName="Players">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Player</h2>
        <form:form modelAttribute="player" class="form-horizontal" action="/players/save">
            <div class="form-group has-feedback">
                <sevenislands:inputField label="FirstName" name="firstName"/>
                <sevenislands:inputField label="Surname" name="surname"/>
                <sevenislands:inputField label="Email" name="email"/>
                <!--<sevenislands:inputField label="Username" name="user"/>-->
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="playerId" value="${player.id}"/>
                    <input type="hidden" name="version" value="${player.version}"/>
                    <button class="btn btn-default" type="submit">Save Player</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</sevenislands:layout>
