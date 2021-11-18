<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="SevenIslands" tagdir="/WEB-INF/tags" %>

<SevenIslands:layout pageName="achievements">
    <h2>
        <c:if test="${achievement['new']}">New </c:if> Achievement
    </h2>
    <form:form modelAttribute="achievement" class="form-horizontal" id="add-achievement-form">
        <div class="form-group has-feedback">
            <div class="row">
                <SevenIslands:inputField label="MinValue" name="minValue"/>
                <SevenIslands:inputField label="Name" name="name"/>
                <SevenIslands:inputField label="Description" name="description"/>
                <SevenIslands:inputField label="Icon" name="icon"/>
            </div>
            <br>
            <div class="row">
                <label class="col-sm-2 control-label"
                    for="type-input">Achivement Type</label>
                <div class="col-sm-4">
                    <select id="type-input" name="achievementType" class="selectpicker">
                        <option value="GOLD">Gold</option>
                        <option value="SILVER">Silver</option>
                        <option value="BRONZE">Bronze</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <label class="col-sm-2 control-label"
                    for="parameter-input">Achievement Parameter</label>
                <div class="col-sm-4">
                    <select id="parameter-input" name="parameter" class="selectpicker">
                        <option value="POINTS">Points</option>
                        <option value="WINS">Wins</option>
                        <option value="LOSES">Loses</option>
                        <option value="GAMES_PLAYED">Games Played</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${achievement['new']}">
                        <button class="btn btn-default" type="submit">Add Achievement</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Achievement</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</SevenIslands:layout>
