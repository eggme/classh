<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-10
  Time: 오전 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/css/views/admin/memberList.css">
<script src="/js/views/admin/memberList.js"></script>


<div class="main_wrap management_main_wrap_template">
    <div class="board_flex_wrap management_board_flex_wrap">
        <div class="member_list_title management_title_menu_template">회원 관리</div>
        <div class="member_search_area management_title_search_area_template">
            <div class="select_box management_select_box_template">
                <div class="select_caret_box management_select_caret_box_template">
                    <div class="select management_select_template">회원번호</div>
                    <div class="select_caret management_select_caret_template"><div class="caret"></div></div>
                </div>
                <div class="select_click_box management_select_click_box_template hidden">
                    <div class="menu_1 management_select_menu_template">회원번호</div>
                    <div class="menu_2 management_select_menu_template">아이디</div>
                    <div class="menu_3 management_select_menu_template">닉네임</div>
                    <div class="menu_4 management_select_menu_template">상태</div>
                </div>
            </div>
            <div class="input_box management_input_box_template">
                <input type="text" placeholder="검색" class="input_component management_input_component_template">
            </div>
        </div>
        <div class="member_table management_object_table_template">
            <div class="member_table_head management_object_table_head_template">
                <div class="table_menu_template management_table_menu_template">회원번호</div>
                <div class="table_menu_template management_table_menu_template">프로필 사진</div>
                <div class="table_menu_template management_table_menu_template">아이디</div>
                <div class="table_menu_template management_table_menu_template">이름</div>
                <div class="table_menu_template management_table_menu_template">닉네임</div>
                <div class="table_menu_template management_table_menu_template">이메일</div>
                <div class="table_menu_template management_table_menu_template">휴대폰 번호</div>
            </div>
            <div class="member_table_body management_object_table_body_template">
                <c:choose>
                    <c:when test="${fn:length(list) > 0}">
                        <c:forEach var="member" items="${list}" varStatus="mstatus">
                            <div class="member_list_template management_object_list_template" data-id="${member.id}">
                                <div class="table_value_template management_table_menu_template member_id">
                                    <c:out value="${member.id}" />
                                </div>
                                <div class="table_value_template management_table_menu_template management_image_template">
                                    <img class="member_image management_object_image" src="${member.profile}">
                                </div>
                                <div class="table_value_template management_table_menu_template member_username">
                                    <c:out value="${member.username}" />
                                </div>
                                <div class="table_value_template management_table_menu_template member_memberName">
                                    <c:out value="${member.memberName}"/>
                                </div>
                                <div class="table_value_template management_table_menu_template member_nickName">
                                    <c:out value="${member.nickName}"/>
                                </div>
                                <div class="table_value_template management_table_menu_template member_email">
                                    <c:out value="${member.email}"/>
                                </div>
                                <div class="table_value_template management_table_menu_template member_tel">
                                    <c:out value="${member.tel}"/>
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
                                                <a href="/admin/members/list?page=${currentPage-5}&size=8&sort=id,asc" aria-label="Previous">
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
                                                    <a href="/admin/members/list?page=${p}&size=8&sort=id,asc">
                                                        <c:out value="${p+1}" />
                                                    </a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li>
                                                    <a href="/admin/members/list?page=${p}&size=8&sort=id,asc">
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
                                                <a href="/admin/members/list?page=${pages}&size=8&sort=id,asc" aria-label="Next">
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
                            등록된 회원이 없습니다
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
