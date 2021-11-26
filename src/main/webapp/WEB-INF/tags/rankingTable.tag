<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="players" required="false"%>
<%@ attribute name="parameter" required="true"%>

<div class="ranking-table">

    <table>
        <thead>
            <th>
                <td></td>
                <td>Player</td>
                <td>${parameter}</td>
            </th>
        </thead>
        <tbody>
            <th>
                <td>1</td> <td>J1</td> <td>00000</td>
            </th>
            <th>
                <td>2</td> <td>J2</td> <td>00010</td>
            </th>
            <th>
                <td>3</td> <td>J3</td> <td>00300</td>
            </th>
        </tbody>
    </table>

</div>
