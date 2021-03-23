<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-17
  Time: 오후 3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="java.util.ArrayList"%>
<%@page import="com.model.RecipeDAO"%>
<%@page import="com.model.RecipeDTO"%>
<%
    RecipeDAO dao = new RecipeDAO();
    ArrayList<RecipeDTO> list_type = dao.mainViewTop();
    ArrayList<RecipeDTO> list_totalImg = dao.mainViewTotalImg();

    // 현재 페이지 번호
    int currentPage = 0;
    // 메뉴 하단의 시작 페이지 (ex: 1, 11, 21, 31)
    int startPage = 0;
    // 메뉴 하단의 끝 페이지 (ex: 10, 20, 30, 40)
    int endPage = 0;
    // 한 페이지에서 보여줄 게시글 수
    final int PAGE_IMAGE_COUNT = 9;
    // 총 페이지
    int totalPage = list_totalImg.size()/ PAGE_IMAGE_COUNT;

    if(request.getParameter("current") == null){
        currentPage = 1;
    }else{
        currentPage = Integer.parseInt(request.getParameter("current"));
    }
    if(request.getParameter("start") == null){
        startPage = 1;
    }else{
        currentPage = Integer.parseInt(request.getParameter("start"));
    }
    if(request.getParameter("end") == null){
        endPage = 10;
    }else{
        currentPage = Integer.parseInt(request.getParameter("end"));
    }
%>

<div class="header">
    <h1>Open Sauce</h1>
    <input class="text" name="search" size="32" maxlength="64">
    <input class="button" type="submit" value="Search">
    <nav id="nav">
        <ul>
            <li class="login"><a href="#">로그인</a></li>
            <li class="join"><a href="#">회원가입</a></li>
            <li class="mypage"><a href="#">마이페이지</a></li>
        </ul>
    </nav>
</div>

<div class="topnav">
    <a class="category" href="#">카테고리</a>
    <a class="recommend" href="#">추천 메뉴</a>
</div>

<div class="row">
    <div class="column side">
        <!-- 사이드 여백용도 -->
    </div>

    <div class="column middle">
        <div class="menu">
            <h1>카테고리</h1>
            <h3>오픈 쏘스의 다양한 레시피와 함께하세요</h3>
            <p class="ctmenu">
                <a href="#" class="memu_alltype">전체</a>
                <a href="#" class="menu_type">종류</a>
                <a href="#" class="menu_method">방법</a>
            </p>

            <p class="ctmenu_sub">
                <span class="submenu_type">전체</span>
                <%for(int i=0; i<list_type.size();i++){%>
                <span>
                          <a href="#" class="type_noodle"><%=list_type.get(i).getType()%></a>
                      </span>
                <%if(i%7==0){ %>
                <br>
                <%} %>
                <%} %>
            </p>
        </div>

        <p>전체개수 :<%=list_img.size() %></p>
        <p><%=totalPage %></p>

        <%for (int j = (PAGE_IMAGE_COUNT * (currentPage - 1)); j < ((PAGE_IMAGE_COUNT * (currentPage-1)) + PAGE_IMAGE_COUNT); j++) {%>
            <% if (j % 3 == 0) { %>
                <div class="boxwrap">
            <%}%>
            <div class="box">
                <span class="thum"><img class="resizing" src="<%=list_totalImg.get(j).getImg()%>" alt="샘플이미지"></span>
                <strong class="title_item"><a href="#"><%=list_totalImg.get(j).getName()%>
                </a></strong>
                <p class="desc_item">음식설명<br>as</p>
            </div>
            <% if (j % 3 == 2) { %>
                </div><br><br>
            <%} %>
        <%} %>
        <%if(currentPage != 1){%>
            <a href="./maincategoryPage.jsp?current=<%=(currentPage-1)%>&start=<%=startPage%>&end=<%=endPage%>">이전</a>
        <%}%>
        <%for(int i=startPage; i<endPage; i++){%>
            <a href="./maincategoryPage.jsp?current=<%=i%>&start=<%=startPage%>&end=<%=endPage%>"><%=i%></a>
        <% }%>
        <%if(currentPage != endPage){ %>
            <a href="./maincategoryPage.jsp?current=<%=(currentPage+1)%>&start=<%=startPage%>&end=<%=endPage%>">다음</a>
        <%}else {%>
            <%
                startPage += 10;
                endPage += 10;
            %>
            <a href="./maincategoryPage.jsp?current=<%=(currentPage+1)%>&start=<%=startPage%>&end=<%=endPage%>">다음</a>
        <%}%>
    </div>

    <div class="column side">
        <!-- 사이드 여백용도 -->
    </div>
</div>
<!-- Footer -->
<div class="footer">
    <p>회사소개/광고문의/개인정보방침/이용약관/고객센터</p>
    <p>문의 E: openSsource@gmail.com  F: 062-222-3333(평일 9:00~18:00, 주말 공휴일 휴무)</p>
    <p>copyright@OPENSSOURCE Inc.All Right Reserved</p>
</div>




<%@page import="com.model.RecipeDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.model.RecipeDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>

<!DOCTYPE HTML>
<!--
TXT by HTML5 UP
html5up.net | @ajlkn
Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
    <title>No Sidebar - TXT by HTML5 UP</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="assets/css/category.css" />
    <style>
        .ctmenu_sub{
            text-align : center;
        }
        .ctmenu_sub > span{
            display: inline-block;
            font-size: 20px;
            margin: 12px;
            text-decoration: none;
            background-color: #DB5548;
            color:#F2E3DC;
            width: 120px;
            height: 60px;
            text-align: center;
            line-height: 60px;
            border-radius: 25px;
            font-weight : bold;

        }
        .ctmenu_sub > span > a{
            color : white;
        }

        .blank{
            margin:10em;
        }
        #active2{
            color:grey;
            font-weight : bold;
            padding: 0 2em;
            font-size : large;
        }

    </style>
</head>
<body class="is-preload">

<%
    RecipeDAO dao = new RecipeDAO();
    ArrayList<RecipeDTO> list_type = dao.mainViewTop();
    ArrayList<RecipeDTO> list_all = dao.allData();

    // 현재 페이지 번호
    int currentPage = 0;
    // 메뉴 하단의 시작 페이지 (ex: 1, 11, 21, 31)
    int startPage = 0;
    // 메뉴 하단의 끝 페이지 (ex: 10, 20, 30, 40)
    int endPage = 0;
    // 한 페이지에서 보여줄 게시글 수
    final int PAGE_IMAGE_COUNT = 9;
    // 총 페이지
    int totalPage = list_totalImg.size()/ PAGE_IMAGE_COUNT;

    if(request.getParameter("current") == null){
        currentPage = 1;
    }else{
        currentPage = Integer.parseInt(request.getParameter("current"));
    }
    if(request.getParameter("start") == null){
        startPage = 1;
    }else{
        currentPage = Integer.parseInt(request.getParameter("start"));
    }
    if(request.getParameter("end") == null){
        endPage = 10;
    }else{
        currentPage = Integer.parseInt(request.getParameter("end"));
    }
%>

<div id="page-wrapper">
    <!-- login  -->
    <nav id="nav2">
        <ul>
            <li class="active"><a href="login.jsp" id="active2" > 로그인 </a></li>
            <!-- <li class="active"><a href="perinfo.jsp" id="active2"> 개인정보수정 </a></li>-->
            <!-- <li class="active"><a href="perinfo.jsp" id="active2"> 로그아웃 </a></li>-->

        </ul>
    </nav>
    <!-- Header -->
    <header id="header">
        <div class="logo container">
            <div>
                <h1><a href="index.html" id="logo"><img src="images/OPEN SAUCE-logo-GREEN.png" alt="사진이 나와야 하는데ㅠ"  height="235" width="666" ></img></a></h1>

            </div>
        </div>
    </header>

    <!-- Nav -->
    <nav id="nav">
        <ul>
            <li><a href="main.jsp">Home</a></li>
            <li  class="current">
                <a href="category.jsp">카테고리</a>
                <ul>
                    <li>
                        <a href="category.jsp">종류</a>
                        <ul>
                            <li><a href="category.jsp">면</a></li>
                            <li><a href="category.jsp">반찬</a></li>
                            <li><a href="category.jsp">찜</a></li>
                            <li><a href="category.jsp">기타</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="category.jsp">방법</a>
                        <ul>
                            <li><a href="category.jsp">구이</a></li>
                            <li><a href="category.jsp">국/탕/찌개</a></li>
                            <li><a href="category.jsp">볶음</a></li>
                            <li><a href="category.jsp">기타</a></li>
                        </ul>
                    </li>
                    <li><a href="category.jsp">어쩌구 추가항목</a></li>
                </ul>
            </li>
            <li><a href="rec_menu.jsp">추천 메뉴</a></li>
            <li><a href="right-sidebar.html">최근 본 레시피</a></li>
            <li><a href="no-sidebar.html">제품 홍보</a></li>
        </ul>
    </nav>

    <!-- Main -->
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="content">

                        <!-- Content -->

                        <article class="box page-content">

                            <header>
                                <h2>카테고리</h2>
                                <!-- <p>Semper amet scelerisque metus faucibus morbi congue mattis</p> -->
                                <ul class="meta">
                                    <li class="icon fa-clock">5 days ago</li>
                                    <li class="icon fa-comments"><a href="#">1,024</a></li>
                                </ul>
                            </header>

                            <section>

                                <p class="ctmenu_sub">
                                    <span class="submenu_type">종류</span>
                                    <%for(int i=0; i<categoryList.size();i++){ %>
                                    <span>
                                               <a href="#" class="type_noodle"><%=categoryList.get(i).getType() %></a>
                                           </span>
                                    <%} %>
                                </p>
                            </section>
                        </article>
                    </div>
                </div>
                <div class="col-12">
                    <p>전체개수 :<%=list_all.size() %></p>
                    <p><%=totalPage %></p>
                    <!-- Highlight -->
                    <section class="box features">
                        <h2 class="major"><span>오픈 쏘스의 다양한 레시피와 함께하세요</span></h2>
                        <div>
                            <div class="row">
                            <%for (int j = ((currentPage * currentPage) - 1); j < ((PAGE_IMAGE_COUNT * currentPage) + PAGE_IMAGE_COUNT); j++) {%>
                                <div class="col-3 col-6-medium col-12-small">

                                    <!-- Feature -->
                                    <section class="box feature">
                                        <a href="./foodPage.jsp?num=<%=j%>" class="image featured"><img class ="resizing" src="<%=list_all.get(j).getImg()%>" alt="" /></a>
                                        <h3><a href="#"><%=list_all.get(j).getName()%></a></h3>
                                        <p>
                                            <%=list_all.get(j).getDescription()%>
                                        </p>
                                    </section>
                                </div>

                                <%} %>
                            </div>
                            <%if(currentPage != 1){%>
                        <a href="./maincategoryPage.jsp?current=<%=(currentPage-1)%>&start=<%=startPage%>&end=<%=endPage%>">이전</a>
                            <%}%>
                            <%for(int i=currentPage; i<endPage; i++){%>
                        <a href="./maincategoryPage.jsp?current=<%=i%>&start=<%=startPage%>&end=<%=endPage%>"><%=i%></a>
                            <% }%>
                            <%if(currentPage != endPage){ %>
                        <a href="./maincategoryPage.jsp?current=<%=(currentPage+1)%>&start=<%=startPage%>&end=<%=endPage%>">다음</a>
                            <%}else {%>
                            <%
                                startPage += 10;
                                endPage += 10;
                            %>
                        <a href="./maincategoryPage.jsp?current=<%=(currentPage+1)%>&start=<%=startPage%>&end=<%=endPage%>">다음</a>
                            <%}%>
                        </div>
                    </section>
                    <!--3*3끝  -->

                    <div class="blank">
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer id="footer">
        <div class="container">
            <div class="row gtr-200">
                <div class="col-12">

                    <!-- About -->
                    <section>
                        <h2 class="major"><span>What's this about?</span></h2>
                        <p class = "foot2">회사소개/광고문의/개인정보방침/이용약관/고객센터</p>
                        <p class = "foot2">문의 E: opensauce@gmail.com  F: 062-222-3333(평일 9:00~18:00, 주말 공휴일 휴무)</p>
                        <P class = "foot2">copyright@OFTENSSOURCE Inc.All Right Reserved</P>
                    </section>

                </div>
                <div class="col-12">

                    <!-- Contact -->
                    <section>
                        <h2 class="major"><span>Get in touch</span></h2>
                        <ul class="contact">
                            <li><a class="icon brands fa-facebook-f" href="#"><span class="label">Facebook</span></a></li>
                            <li><a class="icon brands fa-twitter" href="#"><span class="label">Twitter</span></a></li>
                            <li><a class="icon brands fa-instagram" href="#"><span class="label">Instagram</span></a></li>
                            <li><a class="icon brands fa-dribbble" href="#"><span class="label">Dribbble</span></a></li>
                            <li><a class="icon brands fa-linkedin-in" href="#"><span class="label">LinkedIn</span></a></li>
                        </ul>
                    </section>

                </div>
            </div>

            <!-- Copyright -->
            <div id="copyright">
                <ul class="menu">
                    <li>&copy; Untitled. All rights reserved</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
                </ul>
            </div>

        </div>
    </footer>

</div>

<!-- Scripts -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.dropotron.min.js"></script>
<script src="assets/js/jquery.scrolly.min.js"></script>
<script src="assets/js/browser.min.js"></script>
<script src="assets/js/breakpoints.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/main.js"></script>

</body>
</html>