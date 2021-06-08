<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-11
  Time: 오후 2:40
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
    <link rel="stylesheet" href="/css/template/dashboard_layout.css">
    <link rel="stylesheet" href="/css/template/global.css">
    <script type="text/javascript">
        $(function () {
            selectMenu('<tiles:insertAttribute name="menu"></tiles:insertAttribute>');
            var menu = mappingMenu('<tiles:insertAttribute name="menu"></tiles:insertAttribute>');
            console.log(menu);
            if(menu == '학습 대시보드'){
                $('.content_wrap_color').css('background-color', '#f7f7f7');
            }
            $('.menu_template_title').text(menu);
        });
    </script>
</head>
<body>
<div class="wrap">
    <tiles:insertAttribute name="header"></tiles:insertAttribute>
    <div class="menu_template_line">
        <div class="menu_template_title"></div>
    </div>
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