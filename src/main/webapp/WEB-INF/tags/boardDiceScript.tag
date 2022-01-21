<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script  type="text/javascript" >

    // DICE 

const diceRollingTime = 5000;

const diceFaces = {
    1:"/resources/images/dice/Dice1.png",
    2:"/resources/images/dice/Dice2.png",
    3:"/resources/images/dice/Dice3.png",
    4:"/resources/images/dice/Dice4.png",
    5:"/resources/images/dice/Dice5.png",
    6:"/resources/images/dice/Dice6.png",
}

let diceInterval = null;
let dice = document.getElementById("dice");
let diceThrown = '${game.dieThrows}' == 'true';
let diceImg = dice.firstChild.nextSibling;
let diceValue = "${game.valueOfDie}";

startDiceIfPossible();

function startDiceIfPossible() {
    if(diceValue != "" && diceThrown) {
        rollDice();
    }
}

function shuffleDice() {
    if (diceInterval != null) return;
    diceInterval = setInterval(() => {
        let face = Math.floor(Math.random() * 6)+ 1; // Random 1-6
        diceImg.src = diceFaces[face];
    }, 300);
    
}

function stopShufflingDice() {
    clearInterval(diceInterval);
    diceInterval = null;
}

function setDiceValue(diceValue) {
    stopShufflingDice();

    dice.classList.add("dice-rolling");
    dice.classList.remove("dice-stop");
    dice.classList.remove("dice-done");
    shuffleDice();

    setTimeout(() => {
        dice = document.getElementById("dice");
        diceImg = dice.firstChild.nextSibling;

        dice.classList.add("dice-done");
        dice.classList.remove("dice-rolling");
        stopShufflingDice();

        let face = diceValue;
        diceImg.src = diceFaces[face];
    }, diceRollingTime);

}

function rollDice() {
    setDiceValue(diceValue);
}

</script>
