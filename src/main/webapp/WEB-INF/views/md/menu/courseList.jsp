<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-18
  Time: 오후 2:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/css/views/md/menu/courseList.css">
<script src="/js/views/md/menu/courseList.js"></script>

<div class="main_wrap">
    <div class="board_flex_wrap">
        <div class="course_list_title">강의 관리</div>
        <div class="course_search_area">
            <div class="select_box">
                <div class="select_caret_box">
                    <div class="select">강의번호</div>
                    <div class="select_caret"><div class="caret"></div></div>
                </div>
                <div class="select_click_box hidden">
                    <div class="menu_1 select_menu_template">강의번호</div>
                    <div class="menu_2 select_menu_template">강의명</div>
                    <div class="menu_3 select_menu_template">강사이름</div>
                    <div class="menu_4 select_menu_template">상태</div>
                </div>
            </div>
            <div class="input_box">
                <input type="text" placeholder="검색" class="input_component">
            </div>
        </div>
        <div class="course_table">
            <div class="course_table_head">
                <div class="table_menu_template">강의번호</div>
                <div class="table_menu_template">강의 썸네일</div>
                <div class="table_menu_template">강의명</div>
                <div class="table_menu_template">강사</div>
                <div class="table_menu_template">가격</div>
                <div class="table_menu_template">총 수업 시간</div>
                <div class="table_menu_template">총 수업 개수</div>
                <div class="table_menu_template">상태</div>
            </div>
            <div class="course_table_body">
                <c:choose>
                    <c:when test="${fn:length(list) > 0}">
                        <c:forEach var="course" items="${list}" varStatus="course_status">
                            <div class="course_list_template" data-id="${course.id}">
                                <div class="table_value_template table_menu_template course_id">
                                    <c:out value="${course.id}" />
                                </div>
                                <div class="table_value_template table_menu_template image_template">
                                    <img class="course_image" src="${course.courseImg}">
                                </div>
                                <div class="table_value_template table_menu_template course_title">
                                    <c:out value="${course.name}" />
                                </div>
                                <div class="table_value_template table_menu_template course_member_nickName">
                                    <c:out value="${course.instructor.member.nickName}"/>
                                </div>
                                <div class="table_value_template table_menu_template course_price course_price_${course.id}">
                                    <script>
                                        CostSeparator('${course.price}', '.course_price_${course.id}')
                                    </script>
                                </div>
                                <div class="table_value_template table_menu_template course_total_time course_time_${course.id}">
                                    <script>timeFormatKor('${course.getTotalTime()}', '.course_time_${course.id}')</script>
                                </div>
                                <div class="table_value_template table_menu_template course_class_count">
                                    <c:out value="${course.getTotalClassCount()}"/>개의 수업</div>
                                <div class="table_value_template table_menu_template course_status">
                                    <c:out value="${course.courseState.getValue()}"></c:out>
                                </div>
                            </div>
                        </c:forEach>
                        <c:if test="${total ne 0}">
                            <nav class="pagination_wrap">
                                <ul class="pagination">

                                    <c:set var="viewForCount" value="5"/>
                                    <c:set var="currentPage" value="${current}" />
                                    <c:set var="startPage" value="0" />
                                    <c:if test="${currentPage ge viewForCount}">
                                        <fmt:parseNumber var="temp"  integerOnly="true" value="${(currentPage / viewForCount)}"/>
                                        <c:set var="startPage" value="${(temp * viewForCount)}" />
                                    </c:if>
                                    <fmt:parseNumber var="pages"  integerOnly="true" value="${startPage + viewForCount}"/>
                                    <c:if test="${pages ge total}">
                                        <c:set var="pages" value="${total}"/>
                                    </c:if>
                                    <%-- 이전 버튼 --%>
                                    <c:choose>
                                        <c:when test="${startPage eq 0}">
                                            <li  class="disabled">
                                                <a aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="/md/course/list?page=${currentPage-5}&size=8&sort=id,asc" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                    <%-- 페이징 --%>
                                    <c:forEach var="p" begin="${startPage}" step="1" end="${pages-1}">
                                        <c:choose>
                                            <c:when test="${p eq current}">
                                                <li class="active">
                                                    <a href="/md/course/list?page=${p}&size=8&sort=id,asc">
                                                        <c:out value="${p+1}" />
                                                    </a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                        <li>
                                                            <a href="/md/course/list?page=${p}&size=8&sort=id,asc">
                                                                <c:out value="${p+1}" />
                                                            </a>
                                                        </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <%-- 다음 버튼 --%>
                                    <c:choose>
                                        <c:when test="${pages eq total}">
                                            <li class="disabled">
                                                <a aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="/md/course/list?page=${pages}&size=8&sort=id,asc" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </nav>
                        </c:if>

                    </c:when>
                    <c:otherwise>
                        <div class="noSearch_Course">
                            등록된 강의가 없습니다
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>