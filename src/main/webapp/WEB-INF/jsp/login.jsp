<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="login">
  <c:url value="/login" var="loginUrl" />
  <form action="${loginUrl}" class="form-horizontal" method="post">
    <c:if test="${param.error != null}">
      <div class="alert alert-danger" role="alert">
        Invalid username and password.
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    </c:if>
    <c:if test="${param.logout != null}">
      <div class="alert alert-success" role="alert">
        You have been logged out.
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    </c:if>
    <div class="form-group has-feedback">
      <div class="form-group">
        <label class ="col-sm-2 control-label" for="username">Username</label>
        <div class="col-sm-10">
          <input class="form-control" type="text" id="username" name="username" />
        </div>
      </div>
      <div class="form-group">
        <label class ="col-sm-2 control-label" for="password">Password</label>
        <div class="col-sm-10">
          <input class="form-control" type="password" id="password" name="password" />
        </div>
      </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button class="btn btn-default" type="submit">Log in</button>
      </div>
    </div>
  </form>
</SevenIslands:layout>
