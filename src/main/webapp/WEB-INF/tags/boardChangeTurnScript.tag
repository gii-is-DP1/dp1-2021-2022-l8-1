<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<script type="text/javascript">

    // CHANGE TURN
    const ajaxIntervalTime = 3000;
    const gameId = parseInt('${game.id}');
    const currentPlayerId = parseInt('${id_playing}');
    
    window.addEventListener("load", ()=> {

        setInterval(() => {

            loadNewTurn();

        }, ajaxIntervalTime);

    });

    function loadNewTurn() {
            let xhttp = new XMLHttpRequest();
            xhttp.open("GET", "/games/game/" + gameId);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        console.log("SUCCESS");
                        
                        let response = JSON.parse(this.response);
                        let game = response.game;
                        let currentPlayer = game.actualPlayer;
                        let playersIds = response.playersIds;

                        let currentGamePlayerId = playersIds[currentPlayer];
                        
                        let hasChangedPlayer = currentGamePlayerId != currentPlayerId;
                        let onlyRemainsOnePlayer = playersIds.length == 1;
                        if(hasChangedPlayer || onlyRemainsOnePlayer) {
                            window.location.reload();
                        }
                    }
                };
            xhttp.send();
        }

</script>