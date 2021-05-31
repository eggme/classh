<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-25
  Time: 오후 5:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/css/views/instructor/courseCart.css">
<script src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js" type="text/javascript"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="/js/views/instructor/courseCart.js"></script>

<div class="main_warp">
    <div class="main_container">
        <div class="cart_menu_title">
            <span class="fix_title">수강 바구니</span>
            <ul class="process_ul">
                <li class="active">수강 바구니</li>
                <li>&#8594;</li>
                <li>결제</li>
                <li>&#8594;</li>
                <li>결제 완료</li>
            </ul>
        </div>
        <div class="cart_coupon">
            <div class="my_coupon">
                내 쿠폰함 <i class="fas fa-ticket-alt"></i> <i class="fas fa-chevron-right"></i>
            </div>
            <div class="my_coupon">
                내 위시리스트 <i class="fas fa-heart"></i> <i class="fas fa-chevron-right"></i>
            </div>
        </div>
        <div class="row">
            <c:choose>
                <c:when test="${!(list eq null)}">
                    <c:choose>
                        <c:when test="${fn:length(list) eq 0}">
                            <div class="col-md-12 empty_cart">
                                <div class="empty_cart_value">
                                    <div class="empty_cart_icon">
                                        <i class="fas fa-shopping-cart"></i>
                                    </div>
                                    <div class="empty_cart_title">바구니에 담긴 강의가 없습니다.<br>나를 성장시켜 줄 좋은 지식들을 찾아보세요.</div>
                                    <div class="empty_cart_button_area">
                                        <div class="empty_cart_button_template show_courseList">강의 리스트 보기</div>
                                        <div class="empty_cart_button_template show_my_wishList">내 위시리스트 보기</div>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:when test="${fn:length(list) ge 1}">
                            <c:choose>
                                <c:when test="${fn:length(list) eq 1}">
                                <c:forEach var="course" items="${list}" varStatus="courseStatus">
                                    <input type="hidden" class="total_title" value='${course.name}'/>
                                </c:forEach>
                            </c:when>
                                <c:otherwise>
                                <c:set var="length" value="${fn:length(list)}"/>
                                <c:set var="courseName" value="a" />
                                <c:forEach var="course" items="${list}" varStatus="index">
                                    <c:if test="${index.first}">
                                        <c:set var="courseName" value="${course.name}" />
                                    </c:if>
                                </c:forEach>
                                <input type="hidden" class="total_title" value='${courseName} ${"외"} ${(length-1)} ${"건"}'/>
                            </c:otherwise>
                            </c:choose>
                            <c:set var="total_price" value="0" />
                            <div class="col-md-8">
                                <div class="flex_wrap_course">
                                    <c:forEach var="course" items="${list}" varStatus="course_status">
                                        <c:set var="total_price" value="${total_price + course.price}"/>
                                        <div class="course_box" data-value="${course.id}">
                                            <div class="flex_wrap">
                                                <div class="img_box">
                                                    <img src="${course.courseImg}">
                                                </div>
                                                <div class="text_box">
                                        <span class="course_tile" data-url="${course.url}">
                                            <c:out value="${course.name}"/>
                                        </span>
                                                </div>
                                            </div>
                                            <div class="price_buttons">
                                                <div class="price_box">
                                                    &#x20a9; <span class="course_price course_${course.id}"></span>
                                                    <script>
                                                        CostSeparatorKR('${course.price}', '.course_${course.id}')
                                                    </script>
                                                </div>
                                                <div class="buttons">
                                                    <div class="wishlist_box">위시리스트 이동</div>
                                                    <form method="post" action="/course/delete/cart">
                                                        <input type="hidden" name="course_id" value="${course.id}">
                                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                        <div class="deleteCourse">바구니에서 삭제</div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="flex_wrap_total">
                                    <div class="total_box">
                                        <div class="total_price">
                                            <span>총계 :</span>
                                            <span class="total_price_content price">&#x20a9;
                                    <span class="total_price_content_value">
                                        <script>
                                            CostSeparatorKR('${total_price}', '.total_price_content_value')
                                        </script>
                                    </span>
                                    </span>
                                        </div>
                                        <div class="user_name_box">
                                            <div class="name_menu">이름</div>
                                            <div class="name_menu_content">
                                                <input type="text" class="user_name"/>
                                            </div>
                                        </div>
                                        <div class="user_tel_box">
                                            <div class="tel_menu">전화번호(-제외 숫자만)</div>
                                            <div class="tel_menu_content">
                                                <input type="number" class="user_tel"/>
                                            </div>
                                        </div>
                                        <div class="user_email_box">
                                            <div class="email_menu">이메일 주소</div>
                                            <div class="email_menu_content">
                                                <input type="text" class="user_email"/>
                                            </div>
                                        </div>
                                        <div class="privacy_policy_box">
                                            <div><input type="checkbox" class="privacy_policy"/><span class="pp_text"> (필수) 구매조건 및 개인정보취급방침 동의</span>
                                            </div>
                                            <div class="hidden_text">
                                                구매조건 및 결제대행 서비스 약관 동의하여야 합니다.
                                            </div>
                                        </div>
                                    </div>
                                    <div class="charge_box">
                                        결제하기
                                    </div>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <div class="col-md-12 empty_cart">
                        <div class="empty_cart_value">
                            <div class="empty_cart_icon">
                                <i class="fas fa-shopping-cart"></i>
                            </div>
                            <div class="empty_cart_title">바구니에 담긴 강의가 없습니다.<br>나를 성장시켜 줄 좋은 지식들을 찾아보세요.</div>
                            <div class="empty_cart_button_area">
                                <div class="empty_cart_button_template show_courseList">강의 리스트 보기</div>
                                <div class="empty_cart_button_template show_my_wishList">내 위시리스트 보기</div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>