<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<sevenislands:gameLayout title="Lobby" pageName="Lobby">

    <span id="left-section">
        <h2>Game details</h2>

        <div class="section-content">
            <p><strong>Name: </strong> <c:out value="${game.name}"/></p>
            <p><strong>Room Code: </strong><c:out value="${game.code}"/></p>

            <c:choose>
                <c:when test="${game.player.id==player.id}">
                    <a href="/games/delete/${game.id}" class="btn btn-default">Cancel</a>
                    <c:if test="${totalplayers>1}">
                        <a href="/boards/${game.code}/init" class="btn btn-default">Start match</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <a href="/games/exit/${game.id}" class="btn btn-default">Exit</a>
                </c:otherwise>
            </c:choose>
        </div>

    </span>
    
    <span id="center-section">
        <h2>Party members</h2>

        <div class="section-content">
            <sevenislands:playerList players="${game.players}"/>
        </div>

    </span>

    <span id="right-section">
        <h2>Friends</h2>
        <div class="section-content">

        </div>
    </span>

    <script>

        var lastPlayers = new Array();

        window.onload= ()=>{

            setInterval(() => {

                loadPlayers();

            }, 1000);

        };

        function loadPlayers() {
            
            const gameId = '${game.id}';

            xhttp = new XMLHttpRequest();
            xhttp.open("GET", "/games/players/" + gameId);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        console.log("SUCCESS");

                        let response = JSON.parse(this.response);
                        var players = response.players;
                        
                        let hasChanged = checkIfPlayersChanged(players, lastPlayers);
                        lastPlayers = [...players];

                        if(hasChanged) {
                            updateTable(players);
                        }
                        
                    }
                };
            xhttp.send();

        }

        function checkIfPlayersChanged(players, lastPlayers) {
            if(lastPlayers == []) return false;

            let hasChanged = false;

            let hasDifferentSize = players.length != lastPlayers.length;
            if(hasDifferentSize) return true;

            for(let i=0; i++; i<players.length) {
                let p1 = player[i];
                let p2 = lastPlayers[i];

                if(p1.id != p2.id) return true;
                if(p1.user.username != p2.user.username) return true;
            }
            console.log(hasChanged);
            return hasChanged;
        }

        function updateTable(players) {
            var playersList = document.querySelector(".player-list");

            // Empty the table
            playersList.innerHTML = "";
            
            playerItems = createPlayerItems(players);
            insertItemsInList(playersList, playerItems);

        }

        function createPlayerItems(players) {

            let playerItems = [];
            for (let player of players) {
                let playerItemHTML = 
                    `<li class="player-item">
                        <a href="/players/profile/\${player.id}" htmlescape="true">

                            <p class="username">\${player.user.username}</p>
                            <img class="profile-photo" src="\${player.profilePhoto == null ? '/resources/images/profile-photo.png' : player.profilePhoto}">
                        
                        </a>
                    </li>`;

                playerItems.push(playerItemHTML);
            }

            return playerItems;
        }

        function insertItemsInList(table, items) {
            let innerHTML = items.reduce((item, acum) => item + acum);
            table.innerHTML = innerHTML;
        }
        
    </script>

</sevenislands:gameLayout>