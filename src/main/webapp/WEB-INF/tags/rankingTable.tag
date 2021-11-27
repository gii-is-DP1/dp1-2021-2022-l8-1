<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="players" required="false"%>
<%@ attribute name="parameter" required="true"%>

<div class="ranking-table">


    <div class="table-header">
        <p style="width: 33px"></p>
        <p style="width: 94px">Player</p>
        <p>${parameter}</p>
    </div>

    <div class="table-body">
        <table>
            <tbody>
                <c:forEach items="${[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]}" var="position">
                <tr>
                    <td class="pos-col">${position}</td> <td>J1</td> <td>00000</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>


</div>
