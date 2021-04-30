<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-11
  Time: 오후 3:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<script>
    tinymce.init({
        mode : "textareas",
        selector : "#self_editor",
        height : 250,
        language_url : "/js/ko_KR.js",
        toolbar : 'undo redo | code',
        content_style : '//www.tinymce.com/css/codepen.min.css'
    });
</script>
<script src="/js/profile.js"></script>
<link rel="stylesheet" href="/css/profile.css">
<div class="profile_content_wrap">
    <div class="top_title">
        <div class="border_bottom">
            <span class="top_title_text">프로필 설정</span>
        </div>
    </div>
    <div class="card_container">
        <div class="img_container">
            <form class="image_form" action="/member/modify/image" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="file" class="uploadImage" name="image" style="display:none" onchange="changeImage(this)">
                <div class="circle_img_wrap">
                    <img src="${member.profile}" class="profile_img"/>
                </div>
            </form>
        </div>

        <div class="text_container">
            <form method="post" action="/member/modify/name" class="name_form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="name_container">
                    <div class="name_menu menu_module">닉네임</div>
                    <div class="name_input">
                        <input type="text" class="name_input_area input_area_module" name="name"
                               placeholder="닉네임을 입력해주세요" value="${member.nickName}">
                    </div>
                </div>
                <div class="save_container">
                    <div class="save_button" onclick="submitForm('.name_form')">저장하기</div>
                </div>
            </form>
        </div>
    </div>
    <div class="card_container">
        <div class="self_container">
            <form method="post" action="/member/modify/self" class="self_form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="self_container">
                    <div class="self_menu menu_module">자기소개</div>
                    <div class="self_input">
                        <textarea id="self_editor" name="self">${member.selfIntroduce}</textarea>
                    </div>
                </div>
                <div class="save_container">
                    <div class="save_button" onclick="submitForm('.self_form')">저장하기</div>
                </div>
            </form>
        </div>
    </div>
    <div class="card_container">
        <div class="text_container">
            <form method="post" action="/member/modify/password" class="password_form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="password_container container_module">
                    <div class="password_menu menu_module">비밀번호 변경</div>
                </div>
                <div class="password_input">
                    <input type="password" class="current_password_input_area input_area_module margin_bottom_24"
                           name="current_pw" placeholder="현재 비밀번호"/>
                    <input type="password" class="new_password_input_area input_area_module margin_bottom_24"
                           name="new_pw" placeholder="새 비밀번호"/>
                    <input type="password" class="confirm_password_input_area input_area_module" name="confirm_pw"
                           placeholder="새 비밀번호 확인"/>
                </div>
                <div class="save_container">
                    <div class="save_button" onclick="submitForm('.password_form')">저장하기</div>
                </div>
            </form>
        </div>
    </div>
</div>