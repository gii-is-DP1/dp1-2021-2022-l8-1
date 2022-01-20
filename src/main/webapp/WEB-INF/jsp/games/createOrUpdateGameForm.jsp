<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:layout pageName="games">
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

        <div id="background">
            <div id="sun">
                <div id="sun-shadow"></div>
            </div>
            <div class="waves">
                    <div class="wave circulo a" ></div>
                    <div class="wave circulo b" ></div>
                    <div class="wave circulo c" ></div>
            </div>
        </div>
        
        <c:if test="${errorMessage != null}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}"></c:out>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                </button>
          </div>
         </c:if>
        <h2>
            <c:if test="${game['new']}">New </c:if> Game
        </h2>
        
        <c:choose>
            <c:when test="${game['new']}">
                <form:form modelAttribute="game"
                   class="form-horizontal" action="/games/save">
                   <div class="form-group has-feedback">

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="row">
                                <sevenislands:inputField label="Name of room" name="name" />
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
                            <button class="btn btn-default" type="submit">Create game</button>
                        </div>
                    </div>
                </form:form>
            </c:when>
            <c:otherwise>
                <form:form modelAttribute="game"
                   class="form-horizontal">
                    <input type="hidden" name="id" value="${game.id}"/>
                    <input type="hidden" name="actualPlayer" value="${game.actualPlayer}"/>
                    <input type="hidden" name="numberOfTurn" value="${game.numberOfTurn}"/>
                    <div class="form-group has-feedback">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Game</label>
                            <div class="col-sm-10">
                                <c:out value="Name: ${game.name} - Room code: ${game.code} - Jugador Actual: ${game.actualPlayer}"/>
                            </div>
                        </div>
                        <sevenislands:inputField label="Name" name="name"/>
                        <div class="row">
                            <label class="col-sm-2 control-label"
                                for="privacity-input">Privacity
                            </label>
                            <div class="col-sm-10">
                                <select id="privacity-input" name="privacity" class="selectpicker">
                                    <option value="PUBLIC">Public</option>
                                    <option value="PRIVATE">Private</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <c:choose>
                                <c:when test="${game['new']}">
                                    <button class="btn btn-default" type="submit">Add Game</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-default" type="submit">Update Game</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </form:form>
            </c:otherwise>
        </c:choose>
        
    </jsp:body>
</sevenislands:layout>
