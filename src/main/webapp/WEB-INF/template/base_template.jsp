<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-09
  Time: 오후 1:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html>
<head>
    <meta charset="utf-8">
    <title><tiles:getAsString name="title" /></title>
    <link rel="stylesheet" href="/css/template/global.css">
</head>
<body>
    <div style="background-color: gray; height : 50px;">
        <tiles:insertAttribute name="header" />
    </div>

    <div style="background-color: yellow; height : 50px;">
        <tiles:insertAttribute name="body" />
    </div>

    <div style="background-color: blue; height : 50px;">
        <tiles:insertAttribute name="footer" />
    </div>
</body>
</html>
