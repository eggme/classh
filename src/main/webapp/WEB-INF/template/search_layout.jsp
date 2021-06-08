<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-27
  Time: 오후 4:11
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
    <link rel="stylesheet" href="/css/template/search_layout.css">
    <script type="text/javascript">
        $(function () {
            console.log('<tiles:insertAttribute name="menu"></tiles:insertAttribute>');
        });
    </script>
</head>
<body>
<div class="wrap">
    <tiles:insertAttribute name="header"></tiles:insertAttribute>
    <div class="content_wrap_color">
        <div class="content">
            <tiles:insertAttribute name="aside"></tiles:insertAttribute>
            <div class="page_content">
                <tiles:insertAttribute name="body"></tiles:insertAttribute>
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>
</body>
</html>