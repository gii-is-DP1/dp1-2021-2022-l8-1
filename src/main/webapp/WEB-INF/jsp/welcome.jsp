<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<sevenislands:simpleLayout pageName="home" title="Welcome">

    <div id="mid-section">

        <span id="left-section">
            <h2>Project ${title}</h2>
            <p><h2>Group ${group}</h2></p>
            <p><ul>
                <c:forEach items="${persons}" var="person">
                    <li>
                        <c:out value="${person.firstName} ${person.surname}" />
                    </li>
                </c:forEach>
            </ul></p>
        </span>

        <span id="right-section">
            <div id="welcome-logo">
                <spring:url value="/resources/images/logoUS.png" htmlEscape="true" var="usLogo"/>
                <img class="img-responsive" src="${usLogo}"/>
            </div>
        </span>

    </div>

<<<<<<< HEAD
</sevenislands:simpleLayout>
=======
    <script>
        var face0=new Image()
        face0.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9ce32f-dado1.png"
        var face1=new Image()
        face1.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9d15f6-dado2.png"
        var face2=new Image()
        face2.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9d48bd-dado3.png"
        var face3=new Image()
        face3.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9d7bc5-dado4.png"
        var face4=new Image()
        face4.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9daa7f-dado5.png"
        var face5=new Image()
        face5.src="https://www.lawebdelprogramador.com/usr/147000/147685/527560a9ddd30-dado6.png"
    </script>
 
 
    <img src="" name="mydice">
 
    <form>
    <input type="button" value="Lanza dado" onClick="lanzar()">
    </form>
 
    <script>
    function lanzar()
    {
        var randomdice=Math.round(Math.random()*5);
        document.images["mydice"].src=eval("face"+randomdice+".src");
    }
    </script>
</SevenIslands:layout>
>>>>>>> b08c6595d843010838fb01b04ee245bff9654e6b
