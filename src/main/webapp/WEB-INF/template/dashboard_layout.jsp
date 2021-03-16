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
    <script src="/js/global.js"></script>
    <link href="/css/dashboard_layout.css" rel="stylesheet">
    <script src="/js/boardBase.js"></script>
    <script type="text/javascript">
        $(function () {
            selectMenu('<tiles:insertAttribute name="menu"></tiles:insertAttribute>');
            var menu = mappingMenu('<tiles:insertAttribute name="menu"></tiles:insertAttribute>');
            $('.menu_template_title').text(menu);
        });
    </script>
    <style type="text/css">
        footer {
            position: absolute;
            left: 0px;
            right: 0px;
            bottom: 0px;
            height: 60px;
            width: 100%;
            background: grey;
            color: white;
        }
        .wrap {
            min-height: 100%;
            position: relative;
            padding-bottom: 60px;
        }
        .menu_template_line {
            width: 100%;
            height: 50px;
            background-color: #333b3d;
            padding: 0.5rem;
            margin: 0 auto;
            display: flex;
            justify-content: center;
            text-align: center;
        }
        .menu_template_title {
            width: 1280px;
            align-self: center;
            color: white;
            font-size: 20px;
            font-weight: 700;
            margin-left: 30px;
            text-align: left;
        }
        .content_wrap_color{
            width: 100%; height: 100%; background-color: #F5F5F5; margin: 0; padding: 0;
        }
    </style>
</head>
<body>
<div class="wrap">
    <tiles:insertAttribute name="header"></tiles:insertAttribute>
    <div class="menu_template_line">
        <div class="menu_template_title"></div>
    </div>
    <div class="content_wrap_color">
        <div class="content">
            <tiles:insertAttribute name="side"></tiles:insertAttribute>
            <div class="page_content">
                <tiles:insertAttribute name="body"></tiles:insertAttribute>
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>
</body>
</html>