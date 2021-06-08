<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-04
  Time: 오전 10:10
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
    <script src="/js/global/customChart.js"></script>
    <link rel="stylesheet" href="/css/template/admin_layout.css"/>
    <link rel="stylesheet" href="/css/template/global.css">
</head>
<body>
<div class="wrap">
    <div class="header_wrap">
        <tiles:insertAttribute name="header"></tiles:insertAttribute>
    </div>

    <div class="flex_wrap">
        <div class="aside_wrap">
            <tiles:insertAttribute name="aside"></tiles:insertAttribute>
        </div>
        <div class="column_wrap">
            <div class="content_wrap">
                <tiles:insertAttribute name="body"></tiles:insertAttribute>
            </div>
            <div class="footer_wrap">
                <tiles:insertAttribute name="footer"></tiles:insertAttribute>
            </div>
        </div>
    </div>
</div>
</body>
</html>
