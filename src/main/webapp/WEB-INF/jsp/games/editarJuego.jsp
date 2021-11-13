<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>


<SevenIslands:layout pageName="Games">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            }) ;
        </script>

        <script>
            let btnPublic = document.getElementById("id-public");
            let btnPrivate = document.getElementById("id-private");
            var privacity;
            btnPublic.onclick = function(){
                privacity = PUBLIC;

            }
            btnPrivate.onclick = function(){
                privacity = PRIVATE;

            }
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Game</h2>

        <form:form modelAttribute="game" class="form-horizontal" action="/games/save ">
            <div class="form-group has-feedback">
                <SevenIslands:inputField label="Name of room" name="name"/>
                <SevenIslands:inputField label="Code of the room" name="code"/>
                <button class="btn btn-default" type="button" id="id-public">Public</button>
                <button class="btn btn-default" type="button" id="id-private">Private</button>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="gameId" value="${game.id}"/>
                    <!--<input type="hidden" name="privacity" value="privacity"/> -->
                    <button class="btn btn-default" type="submit">Save Game</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</SevenIslands:layout>
