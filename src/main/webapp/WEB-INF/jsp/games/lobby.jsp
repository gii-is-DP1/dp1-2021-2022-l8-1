<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<sevenislands:lobbyLayout title="Lobby" pageName="Lobby">

    <span id="left-section">
        <h2>Game details</h2>

        <div class="section-content">
            <p><strong>Name: </strong> <c:out value="${game.name}"/></p>
            <p><strong>Room Code: </strong><c:out value="${game.code}"/></p>

            <c:choose>
                <c:when test="${game.player.id==player.id}">
                    <a id="btn-cancel"  href="/games/delete/${game.id}" class="btn btn-default">Cancel</a>
                </c:when>
                <c:otherwise>
                    <a  id="btn-exit" href="/games/exit/${game.id}" class="btn btn-default">Exit</a>
                </c:otherwise>
            </c:choose>
        </div>

    </span>
    
    <span id="center-section">
        <h2>Party members</h2>

        <div class="section-content">
            <sevenislands:playerList players="${game.players}" animated="true"/>
        </div>

    </span>

    <span id="right-section">
        <h2>Friends</h2>
        <div class="section-content">

        </div>
    </span>

    <script>

        const ajaxIntervalTime = 3000;
        const gameId = '${game.id}';
        const playerId = '${player.id}';
        var lastPlayers = new Array();

        window.onload= ()=>{

            setInterval(() => {

                loadPlayers();
                loadGame();

            }, ajaxIntervalTime);

        };

        // BOARD

        function loadGame() {
            let xhttp = new XMLHttpRequest();
            xhttp.open("GET", "/games/game/" + gameId);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        console.log("SUCCESS");
                        
                        let response = JSON.parse(this.response);
                        let game = response.game;
                        let playersNum = response.numberOfPlayers;
                        tryToEnterBoard(game);
                        tryIfIsPossibleToStart(game, playersNum);
                    }
                };
            xhttp.send();
        }

        function tryToEnterBoard(game) {
            let hasStarted = game.hasStarted;
            if(hasStarted) location.reload();
        }

        function tryIfIsPossibleToStart(game, playersNum) {
            let startButtonStr = `<a id="btn-start" href="/boards/\${game.code}/init" class="btn btn-default">Start match</a>`;
            let parser = new DOMParser();
	        let startButton = parser.parseFromString(startButtonStr, 'text/html').body.firstChild;
            let sectionContent = document.querySelector("#left-section .section-content");
        
            let gameOwnerId = game.player.id;
            if(playerId == gameOwnerId) {
                let startButtonElement = document.getElementById("btn-start");
                let buttonIsDisplayed = startButtonElement != undefined;

                if(!buttonIsDisplayed && playersNum>1) {
                sectionContent.appendChild(startButton);
                }
                if(buttonIsDisplayed && playersNum==1) {
                    sectionContent.removeChild(startButtonElement);
                }
            }
            
        }

        // PLAYERS

        function loadPlayers() {
            let xhttp = new XMLHttpRequest();
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
                    `<li class="player-item player-item-animated">
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

</sevenislands:lobbyLayout>