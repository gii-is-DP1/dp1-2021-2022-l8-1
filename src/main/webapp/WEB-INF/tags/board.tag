<%@ attribute name="board" required="false" rtexprvalue="true" type="org.springframework.samples.SevenIslands.board.Board"
 description="Board to be rendered" %>
<canvas id="canvas" width="${board.width}" height="${board.height}"></canvas>
<img id="source" src="${board.background}" style="display:none">
<script>
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    var image = document.getElementById('source');

    ctx.drawImage(image, 0, 0, ${board.width}, ${board.height});
</script>