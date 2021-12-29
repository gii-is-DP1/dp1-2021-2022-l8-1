<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>
<%@ attribute name="cards" required="false" rtexprvalue="true" type="org.hibernate.collection.internal.PersistentBag" %>
<%@ attribute name="disabled" required="false" rtexprvalue="true" %>


<ul class="deck-list">

    <c:forEach items="${cards}" var="card">

        <div class="form-check">
            <sevenislands:card card="${card}" selectable="${!disabled}"/>
        </div>
        
    </c:forEach>

</ul>