<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-09
  Time: 오후 1:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- main css -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/header.css" >
<script src="/js/header.js"></script>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">
                <img id="logo"  src="/imgs/header/Logo_1.png"/>
            </a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav menus">
                <li class="dropdown">
                    <a class="navbar-link class" href="/search.do?s=">강의들&nbsp;<i class="fas fa-angle-down" style="color:#1dc078;"></i></a>
                    <div class="dropdown-content">
                        <a href="/search.do?category=Java">Java</a>
                        <a href="/search.do?category=Spring">Spring</a>
                        <a href="/search.do?category=SpringBoot">SpringBoot</a>
                        <a href="/search.do?category=Hibernate">Hibernate</a>
                        <a href="/search.do?category=Spring JPA">Spring JPA</a>
                        <a href="/search.do?category=Spring Security">Spring Security</a>
                        <a href="/search.do?category=Bootstrap">Bootstrap</a>
                    </div>
                </li>
                <li><a class="navbar-item">로드맵</a></li>
                <li class="dropdown">
                    <a class="navbar-link">커뮤니티&nbsp;<i class="fas fa-angle-down" style="color:#1dc078;"></i></a>
                    <div class="dropdown-content">
                        <a>묻고 답하기</a>
                        <a>수강평 모아보기</a>
                        <a>강의·기능요청</a>
                    </div>
                </li>
            </ul>
            <div class="nav navbar-right">
                <ul class="nav navbar-nav right_menus">
                    <li><a class="navbar-item right_menus_white"><b>지식공유참여</b></a></li>
                    <sec:authorize access="isAnonymous()"><li><h4><a role="button" class="label label-success btn_board login" style="margin-right: 12px;" href="/requestLogin"><b>로그인</b></a></h4></li></sec:authorize>
                    <sec:authorize access="isAnonymous()"><li><h4><a role="button" class="label label-danger btn_board" href="/signUp"><b>회원가입</b></a></h4></li></sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li>
                            <div class="cart height50">
                                <i class="fas fa-shopping-cart cart_over"></i>
                                <div class="course_cart">
                                    <div class="cart_rotate_box"></div>
                                    <div class="cart_tab">
                                        <div class="tab_course_cart cart_tab_active">수강바구니</div>
                                        <div class="tab_wishlist">위시리스트</div>
                                    </div>
                                    <div class="cart_tab_content">
                                    </div>
                                    <div class="cart_tab_button">
                                        <div class="cart_button">수강 바구니로 이동</div>
                                    </div>
                                </div>
                            </div>
                        </li>

                        <li><h4><a role="button" class="label label-success btn_board" href="/dashboard.do"><b>대시보드</b></a></h4></li>
                        <li class="height50">
                            <a role="button" class="height50 overmenu">
                                <img src="/imgs/header/mini_icon_2.png" class="mini_icon">
                            </a>
                            <div class="mouseover_menu_wrap">
                                <div class="rotate_box"></div>
                                <div class="mouseover_menu_bg">
                                    <div class="profile">
                                        <div class="profile_wrap">
                                            <div class="img_wrap">
                                                <img src="/imgs/header/mini_icon_2.png" />
                                            </div>
                                            <div class="text_wrap">
                                                    <span sec:authentication="name" class="user_id"></span>
                                                <span class="fix_text">지식 공유자</span>
                                            </div>
                                        </div>
                                        <div class="item_warp">
                                            <div class="coupon_wrap">
                                                <div><span>쿠폰</span></div>
                                                <div><span class="items">0</span><span>개</span></div>
                                            </div>
                                            <div class="point_wrap">
                                                <div><span>포인트</span></div>
                                                <div><span class="items">0</span><span>잎</span></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab">
                                        <div class="tab_warp">
                                            <span class="tab_active tab_content" id="student">학생</span>
                                            <span class="tab_content" id="instructor">지식공유</span>
                                        </div>
                                    </div>
                                    <div class="tab_list">
                                        <div class="student_list">
                                            <ul>
                                                <li><a><i class="far fa-play-circle"></i> 이어서 학습하기</a></li>
                                                <li><a><i class="fas fa-book"></i> 내 강의</a></li>
                                                <li><a><i class="fas fa-list-ul"></i> 내 목록</a></li>
                                                <li><a><i class="fas fa-road"></i> 참여중인 로드맵</a></li>
                                                <li><a><i class="far fa-edit"></i> 내 질문 답변</a></li>
                                                <li><a><i class="fas fa-pencil-alt"></i> 강의 노트</a></li>
                                            </ul>
                                        </div>
                                        <div class="instructor_list">
                                            <ul>
                                                <li><a><i class="fas fa-home"></i> 대시보드</a></li>
                                                <li><a href="/courseManagement.do"><i class="fas fa-chalkboard"></i> 강의 관리</a></li>
                                                <li><a href="/createCourse.do"><i class="fas fa-magic"></i> 강의 만들기</a></li>
                                                <li><a><i class="far fa-edit"></i> 질문 리스트</a></li>
                                                <li><a><i class="fas fa-search-dollar"></i> 수익 확인</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="bottom_menu">
                                        <a class="logout"><span>로그아웃</span></a>
                                        <a><span>고객센터</span></a>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </sec:authorize>
                    <!--
                    <li><a class="navbar-item"><i class="fas fa-shopping-cart"></i></a></li>
                    <li><a class="navbar-item"><i class="fas fa-bell"></i></a></li>
                    <li><a class="navbar-item"><img src="/imgs/mini_icon_1.png" /></a></li>
                    -->
                </ul>
            </div>
        </div>
    </div>
</nav>