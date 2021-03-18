<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오전 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="/css/aside/dashboard_aside.css" >
<script src="/js/aside/sideMenu.js"></script>

<div class="aside_warp">
    <aside class="aside_menu">
        <div class="dashboard tag">
            <div class="ul_title">대시보드</div>
            <ul class="tag_ul">
                <li data-url="/course/dashboard" data-menu="instructorDashboard">대시보드</li>
            </ul>
        </div>
        <div class="course_management tag">
            <div class="ul_title">강의 관리</div>
            <ul class="tag_ul">
                <li data-url="/course/list" data-menu="courseList">강의 관리</li>
                <li>질문 리스트</li>
                <li>수강평 리스트</li>
                <li>강의 소식 관리</li>
                <li>수익 확인</li>
                <li>쿠폰 관리</li>
                <li>문의 리스트</li>
            </ul>
        </div>
        <div class="course_maker tag">
            <div class="ul_title">강의 만들기</div>
            <ul class="tag_ul">
                <li data-url="/course/add">강의 만들기</li>
            </ul>
        </div>
        <div class="course_marketing tag">
            <div class="ul_title">내 강의 알리는 법</div>
            <ul class="tag_ul">
                <li>내 강의 알리는 법</li>
                <li>지식공유자 안내서</li>
            </ul>
        </div>
    </aside>
</div>