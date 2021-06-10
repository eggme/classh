<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-11
  Time: 오후 2:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/aside/dashboard_aside.css">
<script src="/js/aside/sideMenu.js"></script>

<div class="aside_warp">
    <aside class="aside_menu">
        <div class="home tag">
            <div class="ul_title">HOME</div>
            <ul class="tag_ul">
                <li data-url="/member/dashboard" data-menu="dashboard">학습 대시보드</li>
                <li data-url="/member/notifications" data-menu="notification">알림</li>
            </ul>
        </div>
        <div class="study_management tag">
            <div class="ul_title">학습 관리</div>
            <ul class="tag_ul">
                <li data-url="/member/list" data-menu="courseList">내 강의</li>
                <li>학습 노트</li>
                <li>내 강의 목록</li>
                <li data-url="/member/questions" data-menu="questionList">내 질문</li>
                <li>문의 내역</li>
            </ul>
        </div>
        <div class="course_management tag">
            <div class="ul_title">수강신청 관리</div>
            <ul class="tag_ul">
                <li>수강 바구니</li>
                <li>위시리스트</li>
                <li>쿠폰함</li>
                <li>내 포인트</li>
                <li>구매 내역</li>
            </ul>
        </div>
        <div class="configuration tag">
            <div class="ul_title">설정</div>
            <ul class="tag_ul">
                <li data-url="/member/profile" data-menu="profile">프로필 설정</li>
                <li>알림 설정</li>
            </ul>
        </div>
    </aside>
</div>