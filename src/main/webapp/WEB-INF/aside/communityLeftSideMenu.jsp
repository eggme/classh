<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-08
  Time: 오후 4:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/aside/dashboard_aside.css">
<script src="/js/aside/communitySideMenu.js"></script>

<div class="aside_warp">
    <aside class="aside_menu">
        <div class="home tag">
            <div class="ul_title">함께 공부해요</div>
            <ul class="tag_ul">
                <li>수강평 <i class="far fa-star"></i></li>
                <%--
                 <li data-url="/member/notifications" data-menu="notification">알림 <i class="far fa-lightbulb"></i></li>
                 --%>
                <li>알림 <i class="far fa-lightbulb"></i></li>
            </ul>
        </div>
        <div class="study_management tag">
            <div class="ul_title">호프런</div>
            <ul class="tag_ul">
                <li >호프런 이야기 <i class="fas fa-scroll"></i></li>
                <li data-menu="communityBoard">서비스 공지 <i class="fas fa-bullhorn"></i></li>
                <li>호프런 소개 <i class="fas fa-leaf"></i></li>
            </ul>
        </div>

    </aside>
</div>