<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-27
  Time: 오후 3:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/css/views/user/searchCourse.css"/>
<script src="/js/views/user/searchCourse.js"></script>

<div class="main_wrap">
    <div class="flex_container">
        <div class="header_menu">
            <div class="header_text">
                <span>전체</span>
            </div>
            <div class="header_input_wrap">
                <form action="/course/search" method="get">
                    <c:set var="val" value=""/>
                    <c:if test="${!(value eq null)}">
                        <c:set var="val" value="${value}"/>
                    </c:if>
                    <input type="text" class="header_input" name="s" placeholder="강의 검색하기" value="${val}"/>
                    <input type="submit" class="search_btn" value="검색" />
                </form>
            </div>
        </div>
        <div class="row section_data_wrap">
            <c:choose>
                <c:when test="${fn:length(list) gt 0}">
                    <c:forEach var="course" items="${list}" varStatus="index">
                        <div class="col-md-3 course_item">
                            <div class="course_item_wrap" data-id="${course.id}">
                                <div class="course_img_box">
                                    <img src="${course.courseImg}" class="course_img_value">
                                </div>
                                <div class="course_content_wrap">
                                    <div class="course_title_box">
                                        <div class="course_title">
                                            <c:out value="${course.name}" />
                                        </div>
                                    </div>
                                    <div class="instructor_name_box">
                                        <div class="instructor_name">
                                            <c:out value="${course.instructor.member.nickName}" />
                                        </div>
                                    </div>
                                    <div class="course_price_box">
                                        <div class="course_price">
                                            &#x20a9;<span class="course_price_${course.id}"></span>
                                            <script>CostSeparator('${course.price}', '.course_price_${course.id}')</script>
                                        </div>
                                    </div>
                                    <div class="c_tag">
                                        <div class="c_tag_value">
                                            <c:out value="${course.courseCategory.getValue()}"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-md-12 no_search_data">
                        <div class="no_search_title">🙈 검색 결과가 없어요! 🙊</div>
                        <div class="no_search_desc">필터를 다시 적용해보시거나 카테고리를 이동해보세요</div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>