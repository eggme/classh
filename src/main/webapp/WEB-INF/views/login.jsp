<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-10
  Time: 오후 3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/login.css">
<script src="/js/login.js"></script>
<div class="login_area">
    <form method="post" action="/login" class="login_form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="image_container">
            <img src="/imgs/header/Logo_1.png" class="login_logo">
        </div>
        <div class="component_container">
            <input class="login_form_input email" type="email" name="username" placeholder="이메일" required>
            <input class="login_form_input password" type="password" name="password" placeholder="비밀번호" required>

            <button type="button" class="login_button">로그인</button>
            <p class="gray-text" style="margin-top: 20px;">비밀번호를 잊어버리셨나요?</p>
            <p class="gray-text underline"><a>비밀번호 찾기</a></p>
            <p class="gray-text">아직 호프런 회원이 아니신가요?</p>
            <p class="gray-text underline"><a href="/signUp.do">회원가입 하기</a></p>
        </div>
    </form>
    <c:if test="${error == true}">
        <script>
            alert('${exception}');
        </script>
    </c:if>
</div>