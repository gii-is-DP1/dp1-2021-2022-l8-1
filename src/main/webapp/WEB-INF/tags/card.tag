<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="card" required="false" rtexprvalue="true" type="org.springframework.samples.SevenIslands.card.Card" %>
<%@ attribute name="withText" required="false" rtexprvalue="true" %>
<%@ attribute name="selectable" required="false" rtexprvalue="true" %>


<li class="card-item ${selectable ? 'card-item-selectable' : '' }">

    <input class="card-checkbox" 
           type="checkbox"
           ${selectable ? '' : 'disabled'}
           name="card[]" value="${card.id}">
    <img 
    class="card-img" 
    src="${card.imageUrl}" 
    alt="Card: ${card.cardType}"
    >  
    <c:if test="${withText}">
        <p>${card.cardType}</p>
    </c:if>
</li>