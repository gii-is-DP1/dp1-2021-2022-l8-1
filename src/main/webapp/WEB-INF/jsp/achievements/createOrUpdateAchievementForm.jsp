<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sevenislands" tagdir="/WEB-INF/tags" %>


<sevenislands:layout pageName="achievements">
    
    <div id="background"></div>

    <c:if test="${errorMessage != null}">
        <div class="alert alert-danger" role="alert">
            <c:out value="${errorMessage}"></c:out>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>

    <h2>
        <c:if test="${achievement['new']}">New </c:if> Achievement 
    </h2>
    
    <c:choose>

        <c:when test="${achievement['new']}">
            
            <form:form modelAttribute="achievement" class="form-horizontal" id="add-achievement-form" action="/achievements/save">
            <div class="form-group has-feedback">
             <div class="row">
                    <sevenislands:inputField label="MinValue" name="minValue"/>
                    <sevenislands:inputField label="Name" name="name"/>
                    <sevenislands:inputField label="Description" name="description"/>
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
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Add Achievement</button>
                </div>
            </div>
        </form:form>
        </c:when>

        <c:otherwise>
            <form:form modelAttribute="achievement" class="form-horizontal" id="add-achievement-form">
            <div class="form-group has-feedback">

                <input type="hidden" name="version" value="${achievement.version}"/>

                <div class="row">
                    <sevenislands:inputField label="MinValue" name="minValue"/>
                    <sevenislands:inputField label="Name" name="name"/>
                    <sevenislands:inputField label="Description" name="description"/>
                    <sevenislands:inputField label="Icon" name="icon"/>
                </div>
                <br>
                <div class="row">
                    <label class="col-sm-2 control-label"
                        for="type-input">Achivement Type</label>
                    <div class="col-sm-4">
                     <select id="type-input1" name="achievementType" class="selectpicker">
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
                        <select id="parameter-input1" name="parameter" class="selectpicker">
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
                        <button class="btn btn-default" type="submit">Update Achievement</button>
                </div>
            </div>
        </form:form>

        </c:otherwise>
    
    </c:choose>
    
     <script type="text/javascript">
        console.log("${achievement.achievementType}");
        var l = document.getElementById("type-input1");
           if("${achievement.achievementType}" == "GOLD"){
               l.value="GOLD";
           }else if("${achievement.achievementType}"  =="SILVER"){
               console.log("hola");
               l.value="SILVER";
           }else{
            l.value="BRONZE"
           }
        
    </script>

    <script type="text/javascript">
        console.log("${achievement.parameter}");
        var f = document.getElementById("parameter-input1");
        if("${achievement.parameter}" == "POINTS"){
           f.value="POINTS";
        }else if("${achievement.parameter}"  =="WINS"){
           console.log("hola");
           f.value="POINTS";
       
        }else if("${achievement.parameter}"  =="LOSES"){
           console.log("hola");
           f.value="LOSES";
        }else{
        f.value="GAMES_PLAYED";
        }
    
        </script>
    
</sevenislands:layout>
