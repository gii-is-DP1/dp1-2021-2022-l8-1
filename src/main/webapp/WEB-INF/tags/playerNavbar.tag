<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:navbarItem active="${name eq 'newgame'}" url="/games/new"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					<span>New game</span>
				</petclinic:navbarItem>
				
			</ul>


			<ul class="nav navbar-nav navbar-right">
                <petclinic:navbarItem active="${name eq 'newgame'}" url="/players/profile"
                title="trigger a RuntimeException to see how it is handled">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <span>Username</span>
                </petclinic:navbarItem>
			</ul>
		</div>



	</div>
</nav>
