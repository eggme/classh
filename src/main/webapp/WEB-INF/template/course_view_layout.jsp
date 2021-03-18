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
    <script src="/js/template/boardBase.js"></script>
    <link rel="stylesheet" href="/css/template/course_view_layout.css">
    <script type="text/javascript"></script>
</head>
<body>
<div class="wrap">
    <tiles:insertAttribute name="header"></tiles:insertAttribute>

    <tiles:insertAttribute name="description"></tiles:insertAttribute>

    <div class="layout_main_content">
        <div class="layout_size_wrap">
            <div class="course_nav">
                <div class="ul_wrap">
                    <ul class="course_ul">
                        <li class="board">대시보드</li>
                        <li class="info active">강의소개</li>
                        <li class="question">질문 & 답변</li>
                        <li class="newly">새소식</li>

                        <li class="management">수강생 관리</li>
                        <a class="no_underline">
                            <li>강의 수정</li>
                        </a>
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
