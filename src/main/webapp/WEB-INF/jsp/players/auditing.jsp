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
                    <th>Action</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>FirstName</th>
                    <th>Surname</th>
                    <th>Profile Photo</th>
                    <th>Password</th>
                    <th>Realised by</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="filterName" value="${filterName}"></c:set>
                <c:forEach items="${players}" var="player">

                        <tr>
                            
                            <td>
                                <c:if test="${player[1] == 0}">
                                    <c:out value="Insert"/>
                                </c:if>

                                <c:if test="${player[1] == 1}">
                                    <c:out value="Update"/>
                                </c:if>

                                <c:if test="${player[1] == 2}">
                                    <c:out value="Delete"/>
                                </c:if>

                                <c:if test="${player[1] == null}">
                                    <c:out value="Password Update"/>
                                </c:if>

                            </td>
                            
                            <td>
                                <c:if test="${player[6] != null}">
                                    <c:out value="${player[6]}"/>
                                </c:if>
                                
                                <c:if test="${player[7] != null}">
                                    <c:out value="${player[7]}"/>
                                </c:if>
                            </td>

                            <td>
                                <c:if test="${player[1] == 2}">
                                    <c:out value="No data"/>
                                </c:if>

                                <c:if test="${player[1] != 2}">

                                    <c:if test="${player[2] == null}">
                                        <c:out value="Email not modified"/>
                                    </c:if>

                                    <c:if test="${player[2] != null}">
                                        <c:out value="${player[2]}"/>
                                    </c:if>

                                </c:if>
                            </td>

                            <td>
                                <c:if test="${player[1] == 2}">
                                    <c:out value="No data"/>
                                </c:if>

                                <c:if test="${player[1] != 2}">

                                    <c:if test="${player[3] == null}">
                                        <c:out value="Firstname not modified"/>
                                    </c:if>

                                    <c:if test="${player[3] != null}">
                                        <c:out value="${player[3]}"/>
                                    </c:if>

                                </c:if>
                            </td>

                            <td>
                                <c:if test="${player[1] == 2}">
                                    <c:out value="No data"/>
                                </c:if>

                                <c:if test="${player[1] != 2}">

                                    <c:if test="${player[4] == null}">
                                        <c:out value="Surname not modified"/>
                                    </c:if>

                                    <c:if test="${player[4] != null}">
                                        <c:out value="${player[4]}"/>
                                    </c:if>

                                </c:if>
                            </td>

                            <td>
                                
                                <c:if test="${player[1] == 2}">
                                    <c:out value="No data"/>
                                </c:if>

                                <c:if test="${player[1] != 2}">

                                    <c:if test="${player[5] == null}">
                                        <c:out value="Profile photo not modified"/>
                                    </c:if>

                                    <c:if test="${player[5] != null}">
                                        <c:out value="${player[5]}"/>
                                    </c:if>

                                </c:if>

                            </td>

                            <td>

                                <c:if test="${player[1] == 2}">
                                    <c:out value="No data"/>
                                </c:if>

                                <c:if test="${player[1] != 2}">

                                    <c:if test="${player[8] == null}">
                                        <c:out value="Password not modified"/>
                                    </c:if>

                                    <c:if test="${player[8] != null}">
                                        <c:out value="${player[8]}"/>
                                    </c:if>

                                </c:if>

                            </td>

                            <td>
                                <c:out value="${player[9]}"/>
                            </td>
                            
                        </tr>

                </c:forEach>

                </tbody>
            </table>

        </div>

    </div>

</sevenislands:simpleLayout>
