<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-09
  Time: 오전 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link href="/css/index.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="/js/index.js"></script>
<!-- Swiper -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/css/swiper.min.css">
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/js/swiper.min.js"></script>--%>
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<script>
    $(function () {
        var swiper = new Swiper('.starter_swiper', {
            slidesPerView: 5,
            spaceBetween: 10,
            slidesPerGroup: 5,
            direction: 'horizontal',
            navigation: { // 네비게이션
                nextEl: '.starter_swiper_next', // 다음 버튼 클래스명
                prevEl: '.starter_swiper_prev', // 이번 버튼 클래스명
            }
        });
        var newly_swiper = new Swiper(".newly_swiper", {
            slidesPerView: 5,
            spaceBetween: 10,
            slidesPerGroup: 5,
            direction: 'horizontal',
            navigation: { // 네비게이션
                nextEl: '.newly_swiper_next', // 다음 버튼 클래스명
                prevEl: '.newly_swiper_prev', // 이번 버튼 클래스명
            }
        });
    });
</script>

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
            <form method="get" action="/course/search">
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
            <div class="course_items">
                <div class="swiper-container starter_swiper">
                    <!-- Additional required wrapper -->
                    <div class="swiper-wrapper">
                        <!-- Slides -->
                        <c:forEach var="course" items="${list}" varStatus="courseStatus">
                            <div class="swiper-slide">
                                <div class="course">
                                    <a class="noATagCss" href="/course/${course.id}">
                                        <div class="course_image">
                                            <img src="${course.courseImg}">
                                        </div>
                                        <div class="course_content">
                                            <div class="c_title"><b><c:out value="${course.name}"/></b></div>
                                            <div class="c_instructor"><c:out
                                                    value="${course.instructor.member.nickName}"/></div>
                                            <div class="c_price">
                                                <c:choose>
                                                    <c:when test="${course.price eq 0}">
                                                        무료
                                                    </c:when>
                                                    <c:otherwise>
                                                        &#x20a9;<span class="course_price_${course.id}"></span>
                                                        <script>CostSeparator('${course.price}', '.course_price_${course.id}')</script>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="c_tag">
                                                <div class="c_tag_value">
                                                    <c:out value="${course.courseCategory.getValue()}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- If we need navigation buttons -->
                    <div class="swiper-button-prev starter_swiper_prev"></div>
                    <div class="swiper-button-next starter_swiper_next"></div>
                </div>
            </div>
        </div>
    </section>
    <section class="sections">
        <div class="container_header">
            <h3><a>따끈따끈, 신규 강의를 만나보세요! 🙋🏻‍♀<span class="new_text">NEW!!</span> <i class="fas fa-angle-right"></i></a>
            </h3>
        </div>
        <div class="course_container">
            <div class="course_items">
                <div class="swiper-container newly_swiper">
                    <!-- Additional required wrapper -->
                    <div class="swiper-wrapper">
                        <!-- Slides -->
                        <c:forEach var="course" items="${list}" varStatus="courseStatus">
                            <div class="swiper-slide">
                                <div class="course">
                                    <a class="noATagCss" href="/course/${course.id}">
                                        <div class="course_image">
                                            <img src="${course.courseImg}">
                                        </div>
                                        <div class="course_content">
                                            <div class="c_title"><b><c:out value="${course.name}"/></b></div>
                                            <div class="c_instructor"><c:out
                                                    value="${course.instructor.member.nickName}"/></div>
                                            <div class="c_price">
                                                <c:choose>
                                                    <c:when test="${course.price eq 0}">
                                                        무료
                                                    </c:when>
                                                    <c:otherwise>
                                                        &#x20a9;<span class="course_price_${course.id}"></span>
                                                        <script>CostSeparator('${course.price}', '.course_price_${course.id}')</script>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="c_tag">
                                                <div class="c_tag_value">
                                                    <c:out value="${course.courseCategory.getValue()}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- If we need navigation buttons -->
                    <div class="swiper-button-prev newly_swiper_prev"></div>
                    <div class="swiper-button-next newly_swiper_next"></div>
                </div>
            </div>
        </div>
    </section>
    <section class="sections">
        <div class="section_flex_wrap">
            <div class="flex_right_wrap">
                <div class="right_section">
                    <div class="member_count_wrap">
                        <span class="member_count">474,859</span>
                        <span>명이</span><br/>
                        <span>호프런과 함께합니다.</span>
                    </div>
                    <div class="right_section_subText">
                        학교에서 배우기 어렵거나 큰 비용을<br/>
                        지불해야만 배울 수 있는 전문적인 지식들을 제공합니다.<br/>
                        오픈 플랫폼의 이점을 통해 다양성과 경제성을 모두 높입니다.<br/>
                    </div>
                    <div class="right_section_button_wrap">
                        <div class="right_section_button_template">수강평 더보기 <i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
            <div class="flex_left_wrap">
                <div class="swiper-container swiper_vertical">
                    <!-- Additional required wrapper -->
                    <div class="swiper-wrapper">
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide vertical_slide">
                            <div class="left_card_template">
                                <div class="left_card_header">
                                    <div class="left_card_username">세뇨르</div>
                                    <div class="left_card_time">10시간 전</div>
                                </div>
                                <div class="left_card_body">
                                    <div class="left_card_body_name">실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화</div>
                                    <div class="left_card_body_icon"><i class="fas fa-angle-right"></i></div>
                                </div>
                                <div class="left_card_footer">
                                    <div class="left_card_footer_desc">자유자재로 사용할 수 있을 때까지 계속 공부하겠습니다.
                                        좋은 강의 감사합니다.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-pagination"></div>
                </div>
                <script>
                    var vertical_swiper = new Swiper(".swiper_vertical", {
                        direction: 'vertical',
                        mousewheelControl: true,
                        slidesPerView: 3,
                    });
                </script>
            </div>
        </div>
    </section>
    <section class="sections full_size">
        <div class="banner_container">
            <div class="banner_items">
                <div class="swiper-container banner_swiper">
                    <!-- Additional required wrapper -->
                    <div class="swiper-wrapper">
                        <div class="swiper-slide banner_slide">
                            <div class="banner_template_wrap" style="background-image: url('/imgs/share-you.jpg'); object-fit: cover;">
                                <div class="banner_template_container">
                                    <div class="banner_template_body">
                                        <div class="banner_template_body_value">
                                            지식을 나눠주세요.<br>쉽게 시작하고 합당한 보상을 받을 수 있어요.
                                        </div>
                                        <div class="banner_template_body_button_area">
                                            <div class="banner_template_body_button">지식공유 알아보기</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide banner_slide">
                            <div class="banner_template_wrap" style="background-image: url('/imgs/hoflearn_user.jpg'); object-fit: cover;">
                                <div class="banner_template_container">
                                    <div class="banner_template_body">
                                        <div class="banner_template_body_value">
                                            모든 팀원들이 호프런 강의들을<br>자유롭게 학습하는 환경을 제공해주세요.
                                        </div>
                                        <div class="banner_template_body_button_area">
                                            <div class="banner_template_body_button">비즈니스 알아보기</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide banner_slide">
                            <div class="banner_template_wrap" style="background-image: url('/imgs/hoflearn_story.jpg'); object-fit: cover;">
                                <div class="banner_template_container">
                                    <div class="banner_template_body">
                                        <div class="banner_template_body_value white_color">
                                            " 저와 비슷한 사람 많을 거라고 생각했어요 "<br>호프런의 탄생 비하인드 스토리가 궁금하신가요?
                                        </div>
                                        <div class="banner_template_body_button_area">
                                            <div class="banner_template_body_button">호프런 탄생 스토리</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide banner_slide">
                            <div class="banner_template_wrap" style="background-image: url('/imgs/hoflearn_desk.jpg'); object-fit: cover;">
                                <div class="banner_template_container">
                                    <div class="banner_template_body">
                                        <div class="banner_template_body_value white_color">
                                            당신은 더 좋은 곳에 갈 수 있어요<br>지금 열려있는 채용공고를 확인해보세요.
                                        </div>
                                        <div class="banner_template_body_button_area">
                                            <div class="banner_template_body_button">공고 확인하기</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- If we need navigation buttons -->
                    <div class="swiper-pagination"></div>
                    <script>
                        var banner_swiper = new Swiper(".banner_swiper", {
                            pagination: {
                                el: ".swiper-pagination",
                                dynamicBullets: true,
                            },
                        });
                    </script>
                </div>
            </div>
        </div>
    </section>
    <section class="sections full_size bg_f9f9f9">
        <div class="section_flex_column_wrap">
            <div class="section_header">
                <div class="section_header_title">다양한 서비스를 신청하세요.</div>
                <div class="section_header_subTitle">호프런의 지식공유자 · 비즈니스 · 대학생 신청방법에 대해 알아보세요.</div>
            </div>
            <div class="section_body section_flex_wrap">
                <div class="section_body_card_template flex_sb">
                    <div class="section_body_card_header">
                        <div class="section_body_card_header_title">지식공유자 되기</div>
                        <div class="section_body_card_header_desc">9개월간 온라인 기술 강의로만 1억원! 나의 지식을 나눠 사람들에게 배움의 기회를 주고, 의미있는 대가를 가져가세요.</div>
                    </div>
                    <div class="section_body_card_footer">
                        <div class="section_body_card_footer_button">
                            <div class="footer_button_value">지식공유자 참여하기</div>
                            <i class="fas fa-arrow-right"></i>
                        </div>
                    </div>
                </div>
                <div class="section_body_card_template flex_sb">
                    <div class="section_body_card_header">
                        <div class="section_body_card_header_title">호프런 비즈니스 신청</div>
                        <div class="section_body_card_header_desc">모든 팀원의 호프런의 강의들을 자유롭게 학습하는 환경을 제공해주세요.
                            업무 스킬에 집중된 콘텐츠를 통해 최신 트렌드의 업무역량을 습득할 수 있습니다.</div>
                    </div>
                    <div class="section_body_card_footer">
                        <div class="section_body_card_footer_button">
                            <div class="footer_button_value">비즈니스 참여하기</div>
                            <i class="fas fa-arrow-right"></i>
                        </div>
                    </div>
                </div>
                <div class="section_body_card_template flex_sb">
                    <div class="section_body_card_header">
                        <div class="section_body_card_header_title">호프런 X 대학생</div>
                        <div class="section_body_card_header_desc">학교와 호프런이 함께 하여,
                            많은 학생 분들께 정해진 커리큘럼 이외에도 필요한 학습을 보완하고, 더욱 성장할 수 있도록 도와드립니다.</div>
                    </div>
                    <div class="section_body_card_footer">
                        <div class="section_body_card_footer_button">
                            <div class="footer_button_value">대학생 신청하기</div>
                            <i class="fas fa-arrow-right"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>