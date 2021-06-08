<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-04
  Time: 오후 5:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/aside/adminSideMenu.css">
<script src="/js/aside/mdSideMenu.js"></script>

<div class="aside_main_wrap">
    <div class="aside_flex_wrap">
        <div class="background_wrap">
            <div class="aside_user_wrap">
                <div class="user_profile_wrap">
                    <img class="profile_img" src="/imgs/default_course_image.png" />
                </div>
                <div class="user_information">
                    <div class="user_nickName"><c:out value="${member.nickName}"/></div>
                    <div class="user_userName"><c:out value="${member.username}"/></div>
                </div>
            </div>

            <div class="aside_search_wrap">
                <input type="text" name="query" class="query" placeholder="검색어를 입력하세요">
                <i class="fas fa-search search_icon"></i>
            </div>
        </div>
        <div class="padding_wrap">
            <div class="aside_menu_wrap">
                <ul class="aside_menu">
                    <li class="flex_row_space_between_wrap">
                        <div class="aside_menu_value">공지사항</div>
                        <div class="aside_menu_icon"><i class="fas fa-chevron-down"></i></div>
                    </li>
                    <li class="flex_row_space_between_wrap aside_course_list">
                        <div class="aside_menu_value">강의 관리</div>
                        <div class="aside_menu_icon"><i class="fas fa-chevron-down"></i></div>
                    </li>
                    <li class="flex_row_space_between_wrap aside_instructor_list">
                        <div class="aside_menu_value">강사 관리</div>
                        <div class="aside_menu_icon"><i class="fas fa-chevron-down"></i></div>
                    </li>
                    <li class="flex_row_space_between_wrap">
                        <div class="aside_menu_value">알림 관리</div>
                        <div class="aside_menu_icon"><i class="fas fa-chevron-down"></i></div>
                    </li>
                    <li class="flex_row_space_between_wrap">
                        <div class="aside_menu_value">통계 관리</div>
                        <div class="aside_menu_icon"><i class="fas fa-chevron-down"></i></div>
                    </li>
                    <li class="flex_row_space_between_wrap">
                        <div class="aside_menu_value">Q&A 관리</div>
                        <div class="aside_menu_icon"><i class="fas fa-chevron-down"></i></div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>