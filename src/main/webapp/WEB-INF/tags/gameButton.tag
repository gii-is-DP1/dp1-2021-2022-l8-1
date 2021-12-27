<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pending" required="false" rtexprvalue="true" %>
<%@ attribute name="type" required="true" rtexprvalue="true" %>

<a id="dice-btn" 
    class="game-btn 
           ${pending == 'true' ? 'game-btn-pending' : 'game-btn-disabled'}"
    href="#"
    ${pending == 'true' ? '' : 'disabled'}>
   
    <c:if test="${type == 'dice'}">
        <p>Roll the dice</p>
        <img src="/resources/images/dice/dice_icon.png" alt="Roll the dice">
    </c:if>

    <c:if test="${type == 'island'}">
        <p>Take card</p>
        <img src="/resources/images/cards/cards_icon.png" alt="Take card">
    </c:if>

</a>