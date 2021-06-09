<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-26
  Time: 오전 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="/css/courseManagement.css">
<script src="/js/courseManagement.js"></script>

<div class="main_section">
    <div class="real_course_content">
        <div class="management_wrap">
            <div class="management_title">수강생 관리</div>
            <div class="student_information management_information_template">
                <div class="information_title management_title_template">수강생 정보</div>
                <div class="information_content_wrap">
                    <div class="total_student_count_wrap">
                        강의에 등록한 총 수강생 : <span class="total_student_count"></span> 명
                    </div>
                    <div class="search_query_wrap">
                        검색어를 포함하는 총 수강생 : <span class="search_query_count"></span> 명
                    </div>
                </div>
            </div>
            <div class="student_search_form management_information_template">
                <div class="student_search_title management_title_template">수강생 검색</div>
                <div class="student_search_form">
                    <input type="text" name="name" class="student_name" placeholder="이메일 또는 닉네임으로 검색하기" />
                    <div class="student_search_button">검색</div>
                </div>
            </div>

            <div clsas="student_list_form mnagement_information_template">
                <div class="student_list_title management_title_template">수강생 리스트</div>
                <div class="student_list_filter"></div>
                <div class="student_list_content_wrap">
                    <div class="student_list_content">
                        <div class="student_flex_row">
                            <div class="student_profile_wrap bottom_margin">
                                <div class="student_profile">
                                    <img class="student_profile_image" src="/imgs/mini_icon_1.png">
                                </div>
                            </div>
                            <div class="student_content_wrap">
                                <div class="student_name_status bottom_margin">
                                    <div class="student_name_value">승준</div>
                                    <div class="student_email_value">( <span class="email_holder">kyyeto9984@naver.com</span> )</div>
                                    <div class="student_status_value">자료 다운로드</div>
                                </div>
                                <div class="student_date bottom_margin">
                                    <div class="student_registered_date">
                                        <div class="student_registered_date_title">수강 신청일 : </div>
                                        <div class="student_registered_data_value">2021년 03월 18일</div>
                                    </div>
                                    <div class="separator"> / </div>
                                    <div class="student_started_date">
                                        <div class="student_started_date_title">수강 시작일 : </div>
                                        <div class="student_started_date_value">2021년 03월 19일</div>
                                    </div>
                                </div>
                                <div class="course_ofTime bottom_margin">
                                    <div class="course_ofTime_title">수강기간 : 무제한</div>
                                </div>
                                <div class="course_progress_wrap">
                                    <div class="course_progress_title">진도율 : <span class="current_progress"></span> 강 / <span class="total_progress">${course.getTotalClassCount()}</span> 강</div>
                                    <div class="course_progress_area">
                                        <progress class="course_progress_data" value="20" max="100"></progress>
                                    </div>
                                </div>
                            </div>
                            <div class="student_info_wrap">
                                <div class="student_info_button">상세보기</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="box_wrap">
        <div class="course_box">
            <div class="course_box_warp">
                <form action="/course/add/cart" method="post" class="course_cart_form">
                    <input type="hidden" name="course_id" value="${course.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <sec:authorize access="isAuthenticated()">
                        <sec:authentication var="userobject" property="principal"></sec:authentication>
                        <c:choose>
                            <c:when test="${course.isCourseRegistration(userobject)}">
                                <%-- 로그인이 된 상태에서 해당 유저가 해당 강의에 수강신청이 된 상태 --%>
                                <div class="course_price"></div>
                                <div class="course_status">학습중</div>
                                <div class="learning_box_study learning_box_template" data-id="${course.id}">이어 학습하기</div>
                            </c:when>
                            <c:when test="${userobject.isPutInTheCart(course.id)}">
                                <%-- 로그인이 된 상태에서 해당 유저가 해당 강의에 수강신청이 안돼있고 장바구니에 담긴 상태 --%>
                                <c:choose>
                                    <c:when test="${course.price eq 0}">
                                        <div class="course_price">무료</div>
                                        <div class="course_status"></div>
                                        <div class="learning_box_purchase learning_box_template" data-status="authentication">바로 학습하기</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="course_price">
                                            <script>
                                                CostSeparatorKR('${course.price}', '.course_price');
                                            </script>
                                        </div>
                                        <div class="course_status"></div>
                                        <div class="learning_box_purchase learning_box_template" data-status="authentication">결제하기</div>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <%-- 로그인은 됐는데 해당 유저가 해당 강의에 수강신청이 안된 상태 --%>
                                <c:choose>
                                    <c:when test="${course.price eq 0}">
                                        <div class="course_price">무료</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="course_price">
                                            <script>
                                                CostSeparatorKR('${course.price}', '.course_price');
                                            </script>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="course_status"></div>
                                <div class="learning_box learning_box_template" data-status="authentication">수강신청</div>
                            </c:otherwise>
                        </c:choose>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <%-- 비로그인 시 익명 사용자 접근 시 --%>
                        <c:choose>
                            <c:when test="${course.price eq 0}">
                                <div class="course_price">무료</div>
                            </c:when>
                            <c:otherwise>
                                <div class="course_price">
                                    <script>
                                        CostSeparatorKR('${course.price}', '.course_price');
                                    </script>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="course_status"></div>
                        <div class="learning_box learning_box_template" data-status="anonymous">수강신청</div>
                    </sec:authorize>
                </form>
                <div class="mini_box">
                    <div class="add_box mini_box_content"><i class="far fa-plus-square line_height"></i> 내 목록 추가</div>
                    <div class="share mini_box_content"><i class="fas fa-share-alt line_height"></i> 공유하기</div>
                </div>
            </div>
            <div class="course_subtext">
                <div class="top_margin">지식공유자 : <span class="instructor_name_tab">
                    <c:out value="${course.instructor.member.nickName}"></c:out></span></div>
                <%--  총 수업 수 구하는 로직 --%>
                <c:set var="total_section_class_count" value="0"/>
                <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                    <c:forEach var="class_o" items="${section.courseClasses}" varStatus="status">
                        <c:set var="total_section_class_count" value="${total_section_class_count + 1}"/>
                    </c:forEach>
                </c:forEach>
                <div class="top_margin ">총 <span class="total_class">
                    <c:out value="${total_section_class_count}"></c:out></span>개 수업 · 총 <span class="course_total_time">
                    <script>timeFormatKorWrapper('${course.getTotalTime()}', '.course_total_time');</script>
                </span>
                </div>
                <div class="top_margin">기간 : 평생 무제한 시청</div>
                <div class="top_margin">수료증 : 발급 강의</div>
                <div class="top_margin">수강 난이도 : <span class="course_level"><c:out
                        value="${course.courseLevel.value}"/></span></div>
            </div>
        </div>
    </div>
</div>