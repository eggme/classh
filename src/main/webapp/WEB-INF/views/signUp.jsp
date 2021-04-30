<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-09
  Time: 오후 3:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/1f4456927b.js" crossorigin="anonymous"></script>
<link href="/css/signup.css" rel="stylesheet"/>
<script src="/js/signUp.js"></script>
<div class="main_wrap">
<div class="signup_wrap">
    <div class="align_wrap form-group">
        <h2 class="signup_header">회원가입</h2>
        <form class="signup" id="signupForm" method="post" action="/signUp">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="input_container">
                <label class="input_label">
                    <span class="label_text">이메일</span>
                    <input type="email" class="text_input input_email" name="username" minlength="5" required/>
                </label>
                <div class="email_hidden_message inline"></div>
            </div>
            <div class="input_container">
                <label class="input_label">
                    <span class="label_text">이메일 확인</span>
                    <input type="email" class="text_input email_confirm" name="username_confirm" minlength="5" required/>
                </label>
                <div class="email_confirm_hidden_message inline"></div>
            </div>
            <div class="input_container">
                <label class="input_label">
                    <span class="label_text">비밀번호</span>
                    <input type="password" class="text_input input_password" name="password" minlength="5" required/>
                </label>
                <div class="password_hidden_message inline"></div>
            </div>
            <div class="input_container">
                <label class="input_label">
                    <span class="label_text">비밀번호 확인</span>
                    <input type="password" class="text_input password_confirm" name="password_confirm" minlength="5" required/>
                </label>
                <div class="password_confirm_hidden_message inline"></div>
            </div>
            <div class="signup_button_wrap">
                <div class="signup_button">
                    <button type="button">가입하기</button>
                </div>
            </div>
            <p class="p_text">가입하기를 클릭함으로써 Hoflearn의 이용약관, 개인정보취급방침에 동의합니다.</p>
        </form>
    </div>
</div>
</div>