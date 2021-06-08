<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-08
  Time: 오전 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/css/views/md/menu/instructorList.css">
<script src="/js/views/md/menu/instructorList.js"></script>

<div class="main_wrap">
    <div class="board_flex_wrap">
        <div class="instructor_list_title">강사 관리</div>
        <div class="instructor_search_area">
            <div class="select_box">
                <div class="select_caret_box">
                    <div class="select">강사번호</div>
                    <div class="select_caret"><div class="caret"></div></div>
                </div>
                <div class="select_click_box hidden">
                    <div class="menu_1 select_menu_template">강사번호</div>
                    <div class="menu_2 select_menu_template">아이디</div>
                    <div class="menu_3 select_menu_template">강사이름</div>
                    <div class="menu_4 select_menu_template">상태</div>
                </div>
            </div>
            <div class="input_box">
                <input type="text" placeholder="검색" class="input_component">
            </div>
        </div>
        <div class="instructor_table">
            <div class="instructor_table_head">
                <div class="table_menu_template">강사번호</div>
                <div class="table_menu_template">프로필 사진</div>
                <div class="table_menu_template">아이디</div>
                <div class="table_menu_template">이름</div>
                <div class="table_menu_template">닉네임</div>
                <div class="table_menu_template">이메일</div>
                <div class="table_menu_template">휴대폰 번호</div>
                <div class="table_menu_template">등록 강의 수</div>
            </div>
            <div class="instructor_table_body">
                <c:choose>
                    <c:when test="${fn:length(list) > 0}">
                        <c:forEach var="inst" items="${list}" varStatus="instructor_status">
                            <div class="instructor_list_template" data-id="${inst.member.id}">
                                <div class="table_value_template table_menu_template instructor_id">
                                    <c:out value="${inst.id}" />
                                </div>
                                <div class="table_value_template table_menu_template image_template">
                                    <img class="instructor_image" src="${inst.member.profile}">
                                </div>
                                <div class="table_value_template table_menu_template instructor_username">
                                    <c:out value="${inst.member.username}" />
                                </div>
                                <div class="table_value_template table_menu_template instructor_memberName">
                                    <c:out value="${inst.member.memberName}"/>
                                </div>
                                <div class="table_value_template table_menu_template instructor_nickName">
                                    <c:out value="${inst.member.nickName}"/>
                                </div>
                                <div class="table_value_template table_menu_template instructor_email">
                                    <c:out value="${inst.member.email}"/>
                                </div>
                                <div class="table_value_template table_menu_template instructor_tel">
                                   <c:out value="${inst.member.tel}"/>
                                </div>
                                <div class="table_value_template table_menu_template instructor_course_count">
                                    <c:out value="${inst.totalCourseCount()}"/>개의 강의</div>
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
                                                    <a href="/md/instructor/list?page=${p}&size=8&sort=id,asc">
                                                        <c:out value="${p+1}" />
                                                    </a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li>
                                                    <a href="/md/instructor/list?page=${p}&size=8&sort=id,asc">
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
                                                <a href="/md/instructor/list?page=${pages}&size=8&sort=id,asc" aria-label="Next">
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
                            등록된 강사가 없습니다
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
