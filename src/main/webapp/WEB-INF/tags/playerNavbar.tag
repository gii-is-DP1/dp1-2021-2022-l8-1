<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags"%>
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
			<div class="navbar-collapse collapse navbar-right" id="main-navbar">
				
				<ul class="nav navbar-nav navbar-right">
					<sevenislands:navbarItem active="${name eq 'profile'}" url="/players/profile"
					title="Enter into your profile">
					<span>Username</span>
					</sevenislands:navbarItem>
				</ul>
			
				<ul class="nav navbar-nav navbar-right">
					<sevenislands:navbarItem active="${name eq 'rooms'}" url="#"
					title="Enter rooms page">
					<span>Rooms</span>
					</sevenislands:navbarItem>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<sevenislands:navbarItem active="${name eq 'ranking'}" url="#"
					title="Enter ranking page">
					<span>Ranking</span>
					</sevenislands:navbarItem>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<sevenislands:navbarItem active="${name eq 'forum'}" url="#"
					title="Enter forum page">
					<span>Forum</span>
					</sevenislands:navbarItem>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<sevenislands:navbarItem active="${name eq 'viewerMode'}" url="#"
					title="Enter viewer mode page">
					<span>Viewer Mode</span>
					</sevenislands:navbarItem>
				</ul>
			</div>
		</div>

	</div>
</nav>
