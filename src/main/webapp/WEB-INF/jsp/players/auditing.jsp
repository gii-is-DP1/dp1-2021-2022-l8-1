<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>

<sevenislands:simpleLayout pageName="auditing" title="Players Auditing">

    <script>
       
        function main(){
            debugger;
            
            //can't be use without Thymeleaf template
            //var element = [[${filterName}]];
            console.log(element);
            document.getElementById("filterName").value=element;
        }
        document.addEventListener("DOMContentLoaded", function () {
        main();
        });
     
     </script>
     


    <div id="center-section">

        <div id="table-options">
            <form>
                <label for="filterName">Find by username:</label>
                <input type="text" id="filterName" name="filterName" placeholder="${filterName}">
                <input type="submit" value="Find"> 
            </form>
        </div>

        <div id="players">

            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Created by</th>
                    <th>Created date</th>
                    <th>Last modified by</th>
                    <th>Last modified date</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="filterName" value="${filterName}"></c:set>
                <c:forEach items="${players}" var="player">

                        <tr>
                            <td>
                                <c:out value="${player.user.username}"/>
                            </td>
                            <td>
                                <c:out value="${player.createdBy}"/>
                            </td>
                            
                            <td>
                                <c:out value="${player.createdDate}"/>
                            </td>

                            <td>
                                <c:out value="${player.lastModifiedBy}"/>
                            </td>
                            
                            <td>
                                <c:out value="${player.lastModifiedDate}"/>
                            </td>
                            
                        </tr>

                </c:forEach>

                
                </tbody>
            </table>

        </div>

    </div>

</sevenislands:simpleLayout>
