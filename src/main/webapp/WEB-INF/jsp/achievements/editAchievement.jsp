<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<sevenislands:layout pageName="Achievements">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Achievement</h2>
        <form:form modelAttribute="achievement" class="form-horizontal" action="/achievements/save">
            <div class="form-group has-feedback">

                <input type="hidden" name="version" value="${achiviement.version}"/>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="row">
                            <sevenislands:inputField label="Name" name="name"/>
                            <sevenislands:inputField label="Description" name="description"/>
                            <sevenislands:inputField label="MinValue" name="minValue"/>
                            <sevenislands:inputField label="Icon" name="icon"/>
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
                </div>
                
            </div>
            


            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="achievementId" value="${achievement.id}"/>
                    <button class="btn btn-default" type="submit">Save Achievement</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</sevenislands:layout>
