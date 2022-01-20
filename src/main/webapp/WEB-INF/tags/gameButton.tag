<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pending" required="false" rtexprvalue="true" %>
<%@ attribute name="type" required="true" rtexprvalue="true" %>


<button id="${type == 'dice' ? 'dice-btn' : type == 'skip' ? 'skip-btn' : type == 'travel' ? 'travel-btn' : ''}" 
    class="game-btn ${pending == 'true' ? 'game-btn-pending' : 'game-btn-disabled'}"
    type="button"
    ${pending == 'true' ? '' : 'disabled'}>

    <c:if test="${type == 'dice'}">
        <p>Roll the dice</p>
        <img src="/resources/images/icons/dice_icon.png" alt="Roll the dice">
    </c:if>

    <c:if test="${type == 'skip'}">
        <p>Skip turn</p>
        <img src="/resources/images/icons/skip_icon.png" alt="Skip turn">
    </c:if>

    <c:if test="${type == 'travel'}">
        <p>Travel to island</p>
        <img src="/resources/images/icons/travel_icon.png" alt="Travel to island">
    </c:if>

</button>