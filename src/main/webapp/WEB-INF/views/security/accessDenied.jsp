<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-04-30
  Time: 오전 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<sec:csrfMetaTags/>
<head>

    <title>Hoflearn - 에러 페이지</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/1f4456927b.js" crossorigin="anonymous"></script>
</head>
<style type="text/css">
    .div_wrap{width: 100%; height: 100%; display: flex; flex-direction: column;}
    .header{display: flex; justify-content: center;}
    .header_img_wrap{width: 100px;}
</style>
<body>
<div class="div_wrap">
    <div class="header">
        <div class="header_img_wrap">
            <img src="/imgs/header/Logo_1.png" class="header_img" />
        </div>
    </div>
    <div class="container">
        <div class="message">
            <h1>해당 페이지를 찾지 못했습니다</h1>
            <p>접근 권한이 없거나 삭제되었습니다</p>
            <a href="/">메인페이지로 이동 🏠🏃🏻‍♀</a>
        </div>
        <div class="cat">
            <img src="/imgs/coding_cat.gif" class="coding_cat"/>
        </div>
    </div>
</div>
</body>
</html>
