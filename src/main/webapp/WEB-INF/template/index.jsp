<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-09
  Time: 오전 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="/css/index.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="/js/index.js"></script>
<div class="starter-template">
    <!-- Slider Carousel -->
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1" class=""></li>
            <li data-target="#myCarousel" data-slide-to="2" class=""></li>
            <li data-target="#myCarousel" data-slide-to="3" class=""></li>
            <li data-target="#myCarousel" data-slide-to="4" class=""></li>
        </ol>
        <div class="carousel-inner" role="listbox">
            <div class="item active" style="background-color : #132239">
                <img class="first-slide slide_image" src="/imgs/carousel/carousel1.jpg" alt="First Slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h2 style="text-align: left;">프로그래밍 입문 가이드 '끝판왕'</h2>
                        <p style="text-align: left;">누구에게나 열려있고<br/>누구나 참여 가능한 코딩의 세계</p>
                    </div>
                </div>
            </div>
            <div class="item" style="background-color : #ff9645">
                <img class="second-slide slide_image" src="/imgs/carousel/carousel2.png" alt="Second Slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h2 style="text-align: left;">쇼핑몰 만들어 볼 사람<br/>여기여기 붙어라~</h2>
                        <p style="text-align: left;">쇼핑몰 웹사이트 개발에 필요한 다양한 기술을 배웁니다.<br/>#리엑트 #노드<br/>#몽고DB #리덕스 #Express
                            JS</p>
                    </div>
                </div>
            </div>
            <div class="item" style="background-color : #53C0DD">
                <img class="third-slide slide_image" src="/imgs/carousel/carousel3.jpg" alt="Third Slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h2 style="text-align: left;">함께 배워요<br/>웹 성능 최적화 노하우</h2>
                        <p style="text-align: left;">꿀팁 키트로 실습하는<br/>웹 프론트엔드 성능 최적화</p>
                    </div>
                </div>
            </div>
            <div class="item" style="background-color: #E5EBF7">
                <img class="fourth-slide slide_image" src="/imgs/carousel/carousel4.jpg" alt="Fourth Slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h2 style="text-align: left; color: black;">꾸준히<br/>사랑받는 강의들 👏</h2>
                        <p style="text-align: left; color: black;">검증된 강의들,<br/> 인프런 베스트셀러를 만나보세요!</p>
                    </div>
                </div>
            </div>
            <div class="item" style="background-color: #ffd592">
                <img class="fifth-slide slide_image" src="/imgs/carousel/carousel5.jpg" alt="Fifth Slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h2 style="text-align: left; color: black;">설레는 신규 강의가 도착했어요 💐</h2>
                        <p style="text-align: left; color: black;">새로운 강의는 어떤 것들이 있을까요? <br/>나에게 필요한 강의는 없는지 확인해보세요!</p>
                    </div>
                </div>
            </div>
        </div>
        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <!-- search section -->
    <section class="search">
        <div class="search_content">
            <h3 class="search-text">배우고, 나누고, 성장하세요</h3>
            <form method="get" action="/search.do">
                <div class="focus_wrap">
                    <div class="search_iuput_border">
                        <input class="search_input" name="s" type="text" placeholder="배우고 싶은 지식을 입력해보세요."/>
                        <a class="search_icon"><i class="fa fa-search fa-lg"></i></a>
                    </div>
                    <div class="search_area">
                        <div class="search_loading">
                            <div class="search_icon_wrap">
                                <img class="search_loading_icon" src="/imgs/brand_logo2.png">
                            </div>
                        </div>
                        <div class="search_result">
                            <div class="search_result_wrap">
                                <div class="course_search_wrap">
                                    <span class="course_search_title">강의들</span>
                                </div>
                                <div class="course_search_content_wrap">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </section>
    <section class="sections">
        <div class="container_header">
            <h3><a>여기서 시작해 보세요!&nbsp;&nbsp;<i class="fas fa-angle-right"></i></a></h3>
            <h5>이미 검증된 쉽고 친절한 입문 강의들!!!</h5>
        </div>
        <div class="course_container">
<%--            <div class="course_items">--%>
<%--                <% if (list != null && list.size() > 0) {--%>
<%--                    for (Course course : list) {--%>
<%--                        String category = course.getCategory() != null ? course.getCategory().getName() : "";--%>
<%--                %>--%>
<%--                <div class="course">--%>
<%--                    <a class="noATagCss" href="/courseInfo.do?course_id=<%=course.getCourse_Id()%>">--%>
<%--                        <div class="course_image">--%>
<%--                            <img src="<%=course.getCourseInfo().getCourse_img()%>">--%>
<%--                        </div>--%>
<%--                        <div class="course_content">--%>
<%--                            <div class="c_title"><b><%=course.getTitle()%>--%>
<%--                            </b></div>--%>
<%--                            <div class="c_instructor"><%=course.getName()%>--%>
<%--                            </div>--%>
<%--                            <div class="c_price">--%>
<%--                                <%if (course.getPrice() == 0) {%>--%>
<%--                                무료--%>
<%--                                <%} else {%>--%>
<%--                                &#x20a9;<%=new DecimalFormat("###,###,###,###").format(course.getPrice())%>--%>
<%--                                <%}%>--%>
<%--                            </div>--%>
<%--                            <div class="c_tag">강의 카테고리 : <%=category%>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </a>--%>
<%--                </div>--%>
<%--                <%--%>
<%--                        }--%>
<%--                    }--%>
<%--                %>--%>
<%--            </div>--%>
        </div>
    </section>
</div>