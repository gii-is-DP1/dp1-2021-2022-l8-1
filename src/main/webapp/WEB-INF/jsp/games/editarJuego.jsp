<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>


<SevenIslands:layout pageName="Games">
    <jsp:attribute name="customScript">
        <script type="text/javascript">
            var result           = '';
                var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
                var charactersLength = characters.length;
                for ( var i = 0; i < 9; i++ ) {
                result += characters.charAt(Math.floor(Math.random() * charactersLength));
                }

            window.onload = function () {
                document.getElementById("roomCode").value = result;
            }
        </script>
    </jsp:attribute>
    <jsp:body>

        <div class="row text-center"><h2>New Game</h2></div>
        <br>
        <br>

        <form:form modelAttribute="game" class="form-horizontal" action="/games/save ">
            <div class="form-group has-feedback">

                <div class="row">
                    <div class="col-sm-6">
                        <div class="row">
                            <SevenIslands:inputField label="Name of room" name="name" />
                            <input type="hidden" name="code" id="roomCode"/>
                        </div>
                        <br>
                        <div class="row">
                            <label class="col-sm-2 control-label"
                                for="privacity-input">Privacity</label>
                            <div class="col-sm-10">
                                <select id="privacity-input" name="privacity" class="selectpicker">
                                    <option value="PUBLIC">Public</option>
                                    <option value="PRIVATE">Private</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                
                <br>
                <br>
                <div class="form-group row text-center">
                    <div class="col-sm">
                        <input type="hidden" name="gameId" value="${game.id}" />
                        <button onclick="window.location.href='/welcome';" class="btn btn-default" type="submit">Continue</button>
                    </div>
                </div>
        </form:form>
    </jsp:body>

</SevenIslands:layout>