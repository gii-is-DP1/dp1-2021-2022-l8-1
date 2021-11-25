<!-- <%@ attribute name="size" required="true" rtexprvalue="true" 
 description="Size of the piece to show" %>
 <%@ attribute name="cell" required="true" rtexprvalue="true" type="package org.springframework.samples.SevenIslands.cell"
 description="Piece to be rendered" %>
<script type="text/javascript">
    window.onload = function() {
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        var image = document.getElementById('${cell.card.cardType}');
        ctx.drawImage(image,0 ,0,${size},${size});
    }
 </script> -->