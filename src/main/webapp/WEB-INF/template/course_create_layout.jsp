<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오후 2:14
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
    <script src="/js/template/courseCreateBoardBase.js"></script>
    <link rel="stylesheet" href="/css/template/dashboard_layout.css">
    <link rel="stylesheet" href="/css/template/course_create_layout.css">
</head>
<body>
<div class="wrap">
    <tiles:insertAttribute name="header"></tiles:insertAttribute>
    <div class="menu_template_line">
        <div class="menu_template_title">
            <div class="course_menu_name">내 강의 만들기</div>
            <div class="course_configuration_button">
                <div class="course_preview course_create_base_button" data-id="${course.id}">강의보기</div>
                <div class="course_save course_create_base_button">저장</div>
            </div>
        </div>
    </div>
    <div class="content_wrap_color">
        <div class="content">
            <tiles:insertAttribute name="aside" ></tiles:insertAttribute>
            <div class="page_content">
                <tiles:insertAttribute name="body"></tiles:insertAttribute>
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>
</body>
</html>
