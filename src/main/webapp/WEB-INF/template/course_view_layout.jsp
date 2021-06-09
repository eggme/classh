<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오후 2:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<sec:csrfMetaTags/>
<html>
<head>
    <title><tiles:getAsString name="title"></tiles:getAsString></title>
    <link href="/imgs/favicon/favicon.ico" rel="icon"/>
    <script src="/webjars/jquery/3.5.1/dist/jquery.min.js"></script>
    <script src="/js/template/global.js"></script>
    <link rel="stylesheet" href="/css/template/global.css">
    <script src="/js/template/courseViewBoardBase.js"></script>
    <link rel="stylesheet" href="/css/template/course_view_layout.css">
    <script type="text/javascript">
        $(function(){
            choiceMenu('<tiles:insertAttribute name="menu"></tiles:insertAttribute>');
        });
        function choiceMenu(menu){
            console.log(menu);
            var arr = $('.course_ul li').toArray();
            console.log(arr.length);
            for(var i =0; i<arr.length;i++){
                if($(arr[i]).hasClass(menu)){
                    $(arr[i]).addClass('active');
                }
            }
        }
    </script>
</head>
<body>
<div class="wrap">
    <div class="header_wrap">
        <tiles:insertAttribute name="header"></tiles:insertAttribute>
    </div>

    <tiles:insertAttribute name="description"></tiles:insertAttribute>
    <div class="layout_main_content">
        <div class="layout_size_wrap">
            <div class="course_nav">
                <div class="ul_wrap">
                    <ul class="course_ul">
                        <sec:authentication var="userObj" property="principal"></sec:authentication>
                        <sec:authorize access="isAuthenticated()">
                            <c:if test="${course.isCourseRegistration(userObj)}">
                                <li class="dashboard"><a class="no_underline" href="/course/${course.id}/dashboard">대시보드</a></li>
                            </c:if>
                        </sec:authorize>
                        <li class="info"><a class="no_underline" href="/course/${course.id}">강의소개</a></li>
                        <li class="question"><a class="no_underline" href="/question/select/${course.id}">질문 & 답변</a></li>
                        <li class="newly"><a class="no_underline" href="/notice/${course.id}">새소식</a></li>
                        <sec:authorize access="isAuthenticated()">
                            <sec:authentication property="principal.username" var="user"></sec:authentication>
                            <c:if test="${course.instructor.member.username eq user}">
                                <li class="management"><a class="no_underline" href="/course/${course.id}/management">수강생 관리</a></li>
                                <li><a class="no_underline" href="/course/${course.id}/edit/course_info">강의 수정</a></li>
                            </c:if>
                        </sec:authorize>
                    </ul>
                </div>
            </div>
            <div class="course_main_content">
                <tiles:insertAttribute name="body"></tiles:insertAttribute>
            </div>
        </div>
    </div>

    <tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>
</body>
</html>
