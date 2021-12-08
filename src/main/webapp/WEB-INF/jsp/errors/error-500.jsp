<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<SevenIslands:layout pageName="error-500">
    
    <div class="row">
        <h1>Oops!</h1>
        <h2>There seems to be an internal service error...</h2>
        <h2>Try again later, sorry!</h2>
    </div>

    <div class="row">
        <div class="col-md-4">
            <spring:url value="/resources/images/logoUS.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/4f555c6d-dcfe-4d93-8d13-656f988f8c84/d4ncfbi-b59bf929-4e02-4550-8330-0dd34fc4f7f1.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzRmNTU1YzZkLWRjZmUtNGQ5My04ZDEzLTY1NmY5ODhmOGM4NFwvZDRuY2ZiaS1iNTliZjkyOS00ZTAyLTQ1NTAtODMzMC0wZGQzNGZjNGY3ZjEuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.qJguHtuLi1W2SBR07AVWqpvVH4AO3r92sAfZQQ0HCJ4"/>
        </div>
    </div>
 
</SevenIslands:layout>