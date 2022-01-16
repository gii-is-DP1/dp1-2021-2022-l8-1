<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>
<%@ attribute name="board" required="false" rtexprvalue="true" type="org.springframework.samples.SevenIslands.board.Board"
 description="Board to be rendered" %>

<canvas id="canvas" width="${board.width}" height="${board.height}"> your browser does not support canvas</canvas>

<img id="source" src="${board.background}" style="display:none">
<img id="boar-card" src="/resources/images/cards/Boat.png" style="display:none">
<img id="doublon-card" src="/resources/images/cards/Doublon.png" style="display:none">
<img id="golden_cup-card" src="/resources/images/cards/GoldenCup.png" style="display:none">
<img id="ruby-card" src="/resources/images/cards/Ruby.png" style="display:none">
<img id="diamond-card" src="/resources/images/cards/Diamond.png" style="display:none">
<img id="pearl_necklace-card" src="/resources/images/cards/PearlNecklace.png" style="display:none">
<img id="crown-card" src="/resources/images/cards/Crown.png" style="display:none">
<img id="message_bottle-card" src="/resources/images/cards/MessageBottle.png" style="display:none">
<img id="barrel_of_rum-card" src="/resources/images/cards/BarrelOfRum.png" style="display:none">
<img id="stir-card" src="/resources/images/cards/Stir.png" style="display:none">
<img id="sword-card" src="/resources/images/cards/Sword.png" style="display:none">


<script type="text/javascript">
    
    // BOARD

    window.addEventListener("load", ()=> {
        var image = document.getElementById("source");

        var boar = document.getElementById("boar-card");
        var doublon = document.getElementById("doublon-card");
        var golden_cup = document.getElementById("golden_cup-card");
        var ruby = document.getElementById("ruby-card");
        var diamond = document.getElementById("diamond-card");
        var pearl_necklace = document.getElementById("pearl_necklace-card");
        var crown = document.getElementById("crown-card");
        var message_bottle = document.getElementById("message_bottle-card");
        var barrel_of_rum = document.getElementById("barrel_of_rum-card");
        var stir = document.getElementById("stir-card");
        var sword = document.getElementById("sword-card");

        const cards = {
            "boar": boar,
            "doublon": doublon,
            "golden_cup": golden_cup,
            "ruby": ruby,
            "diamond": diamond,
            "pearl_necklace": pearl_necklace,
            "message_bottle": message_bottle,
            "crown": crown,
            "barrel_of_rum": barrel_of_rum,
            "stir": stir,
            "sword": sword,
        }

        const islandsCoordinates = {
            1: {x:62, y:20},
            2: {x:190, y:20},
            3: {x:325, y:20},
            4: {x:409, y:200},
            5: {x:293, y:325},
            6: {x:172, y:412},
            7: {x:37, y:421},
        }

        const cardHeight = 100;
        const cardWidth = 145;

        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");

        ctx.drawImage(image, 0, 0);

        // Cards
        let islands = {};

        let islandNum = 0;
        let islandCard = "";
        <c:set var="count" value="1" scope="page" />
        <c:forEach items ="${islands}" var="island">
            islandNum = ${count};
            islandCard = "${island.card.cardType}".toLowerCase();

            islands[islandNum] = islandCard;
            <c:set var="count" value="${count + 1}" scope="page"/>
        </c:forEach>

        for(let islandNum in islands) {
            let card = islands[islandNum];

            let cardImg = cards[card];

            let x = islandsCoordinates[islandNum].x;
            let y = islandsCoordinates[islandNum].y;
            
            setTimeout(()=> {
                ctx.drawImage(cardImg, x, y, cardHeight, cardWidth);
            }, 500 * islandNum);
        }

    });

</script>

<!-- <script type="text/javascript">

    // TIMER

    var timeleft = 2;
    var downloadTimer = setInterval(function(){
    timeleft--;
    document.getElementById("countdowntimer").textContent = timeleft;
    if(timeleft <= 0)
    window.location.reload();
        clearInterval(downloadTimer);
    },1000);
</script> -->