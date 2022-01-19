<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<script type="text/javascript">

    // TIMER

    let turnDate = new Date('${game.turnTime}');
    let actualDate = new Date();

    let diffTimeInSeconds = Math.floor(Math.abs((actualDate - turnDate) / 1000));

    let diffSeconds = Math.trunc(diffTimeInSeconds / 60);
    let diffMinutes = Math.trunc(diffTimeInSeconds - diffSeconds * 60);


    window.addEventListener("load", ()=>{
        
        const timerSecs = document.getElementById("turn-secs");
        const timerMins = document.getElementById("turn-mins");

        setInterval(() => {
            updateTimer();
            decrementTime();
        }, 1000);

        function updateTimer() {
        timerSecs.innerHTML = diffSeconds;
        timerMins.innerHTML = diffMinutes;
    }

        function decrementTime() {
            diffTimeInSeconds--;

            diffSeconds = Math.trunc(diffTimeInSeconds / 60);
            diffMinutes = Math.trunc(diffTimeInSeconds - diffSeconds * 60);
        }
    });

</script>