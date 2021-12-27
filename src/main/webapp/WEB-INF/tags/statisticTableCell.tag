<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="value" required="true" rtexprvalue="true"
            description="Value to represent" %>
<%@ attribute name="isTime" required="false" rtexprvalue="true"%>

<th>
    <c:out value="${value == '' ? 0 : value}"/> ${isTime == true ? 's' : ''}
</th>
