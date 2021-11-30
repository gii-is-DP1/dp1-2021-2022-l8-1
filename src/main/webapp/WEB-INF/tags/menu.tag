<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">

		<!-- LOGO -->
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />">
				<img src="/resources/images/7islands-logo-60.png">
			</a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>

		<!-- AUTHENTICATED -->
		<sec:authorize access="isAuthenticated()">
		<div class="navbar-collapse collapse" id="main-navbar">

			<ul class="nav navbar-nav">
				<sevenislands:menuItem url="/games/rooms" title="Enter rooms page">
					<span class="glyphicon glyphicon-th" aria-hidden="true"></span>
					<span>Rooms</span>
				</sevenislands:menuItem>

				<sevenislands:menuItem url="#" title="Enter ranking page">
					<span class="glyphicon glyphicon-equalizer" aria-hidden="true"></span>
					<span>Ranking</span>
				</sevenislands:menuItem>

				<sevenislands:menuItem url="#" title="Enter forum page">
					<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
					<span>Forum</span>
				</sevenislands:menuItem>

				<sevenislands:menuItem url="#" title="Enter viewer mode page">
					<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
					<span>Viewer Mode</span>
				</sevenislands:menuItem>

				<sec:authorize access="hasAuthority('player')">
				<sevenislands:menuItem url="/games/new" title="Create new room for playing">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					<span>New game</span>
				</sevenislands:menuItem>
				</sec:authorize>
			
				<sec:authorize access="hasAuthority('admin')">
				<sevenislands:menuItem url="/players" title="View list of players">
					<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					<span>Players</span>
				</sevenislands:menuItem>
				</sec:authorize>

				<sec:authorize access="hasAuthority('admin')">
				<sevenislands:menuItem url="/achievements" title="View list of achievements">
					<span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
					<span>Achievements</span>
				</sevenislands:menuItem>
				</sec:authorize>
			</ul>

			<!-- PLAYER -->
			<sec:authorize access="hasAuthority('player')">
			<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value='/logout' />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
											<sec:authorize access="hasAuthority('admin')">
												<p>
													<a href="/admins/profile/${id}" class="btn btn-primary btn-block">My Profile</a>	
												</p>
											</sec:authorize>
											<sec:authorize access="hasAuthority('player')">
												<p>
													<a href="/players/profile/${id}" class="btn btn-primary btn-block">My Profile</a>	
												</p>
											</sec:authorize>
											
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>

						</ul></li>
			</ul>
			</sec:authorize>

			<!-- ADMIN -->
			<sec:authorize access="hasAuthority('admin')">
			<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value='/logout' />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>		

											<sec:authorize access="hasAuthority('admin')">
												<p>
													<a href="/admins/profile/${id}" class="btn btn-primary btn-block">My Profile</a>	
												</p>
											</sec:authorize>
											<sec:authorize access="hasAuthority('player')">
												<p>
													<a href="/players/profile/${id}" class="btn btn-primary btn-block">My Profile</a>	
												</p>
											</sec:authorize>
					
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>

						</ul></li>
			</ul>
			</sec:authorize>

		</div>
		</sec:authorize>


		<!-- NOT AUTHENTICATED -->
		<sec:authorize access="!isAuthenticated()">
			<div class="navbar-collapse collapse" id="main-navbar">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value='/login' />">Login</a></li>
					<li><a href="<c:url value='/users/new' />">Register</a></li>
				</ul>
			</div>
		</sec:authorize>
		
	</div>
</nav>


