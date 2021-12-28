<%@ attribute name="board" required="false" rtexprvalue="true" type="org.springframework.samples.SevenIslands.board.Board"
 description="Board to be rendered" %>
<canvas id="canvas" width="${board.width}" height="${board.height}"> your browser does not support canvas</canvas>
<img id="source" src="${board.background}" style="display:none">
<img id="BOAT" src="/resources/images/cards/Boat.png" style="display:none">
<img id="DOUBLON" src="/resources/images/cards/Doublon.png" style="display:none">
<img id="GOLDEN_CUP" src="/resources/images/cards/GoldenCup.png" style="display:none">
<img id="RUBY" src="/resources/images/cards/Ruby.png" style="display:none">
<img id="DIAMOND" src="/resources/images/cards/Diamond.png" style="display:none">
<img id="PEARL_NECKLACE" src="/resources/images/cards/PearlNecklace.png" style="display:none">
<img id="CROWN" src="/resources/images/cards/Crown.png" style="display:none">
<img id="MESSAGE_BOTTLE" src="/resources/images/cards/MessageBottle.png" style="display:none">
<img id="BARREL_OF_RUM" src="/resources/images/cards/BarrelOfRum.png" style="display:none">
<img id="STIR" src="/resources/images/cards/Stir.png" style="display:none">
<img id="SWORD" src="/resources/images/cards/Sword.png" style="display:none">
<!-- <script type="text/javascript">
    window.onload = function() {
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        var image = document.getElementById("source");

        ctx.drawImage(image, 0, 0);
        // ctx.drawImage(image, 0, 0, ${board.width}, ${board.height});
    }
</script>

<script type="text/javascript">
    var timeleft = 2;
    var downloadTimer = setInterval(function(){
    timeleft--;
    document.getElementById("countdowntimer").textContent = timeleft;
    if(timeleft <= 0)
    window.location.reload();
        clearInterval(downloadTimer);
    },1000);
</script> -->