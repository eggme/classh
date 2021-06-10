<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-10
  Time: 오후 1:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<link rel="stylesheet" href="/css/views/admin/noticeList.css" >
<script src="/js/views/admin/noticeList.js"></script>

<div class="main_wrap management_main_wrap_template">
    <div class="board_flex_wrap management_board_flex_wrap">
        <div class="system_notice_title management_title_menu_template">공지사항</div>
        <div class="system_notice_toolbox">
            <div class="notification_write_button">공지사항 작성</div>
        </div>
        <div class="system_notice_table management_object_table_template">
            <div class="system_notice_table_head management_object_table_head_template">
                <div class="table_menu_template management_table_menu_template">공지사항 번호</div>
                <div class="table_menu_template management_table_menu_template">공지사항 이름</div>
                <div class="table_menu_template management_table_menu_template">발신자 이름</div>
                <div class="table_menu_template management_table_menu_template">수신자 이름</div>
                <div class="table_menu_template management_table_menu_template">발송 시간</div>
                <div class="table_menu_template management_table_menu_template">수신 확인 여부</div>
                <div class="table_menu_template management_table_menu_template">알림 타입</div>
            </div>
            <div class="system_notice_table_body management_object_table_body_template">
                <c:choose>
                    <c:when test="${fn:length(list) > 0}">
                        <c:forEach var="notification" items="${list}" varStatus="nstatus">
                            <div class="system_notice_list_template management_object_list_template" data-id="${notification.id}">
                                <div class="table_value_template management_table_menu_template system_notice_id">
                                    <c:out value="${notification.id}" />
                                </div>
                                <div class="table_value_template management_table_menu_template system_notice_title">
                                    <c:out value="${notification.title}"/>
                                </div>
                                <div class="table_value_template management_table_menu_template system_notice_username">
                                    <c:out value="${notification.writer.nickName}" />
                                </div>
                                <div class="table_value_template management_table_menu_template system_notice_memberName">
                                    <c:out value="${notification.member.nickName}"/>
                                </div>
                                <div class="table_value_template management_table_menu_template system_notice_createAt_${notification.id}">
                                    <script>
                                        convertLocalDateTime('${notification.create_at}', '.notification_createAt_${notification.id}');
                                    </script>
                                </div>
                                <div class="table_value_template management_table_menu_template system_notice_isRead">
                                    <c:out value="${notification.read}"/>
                                </div>
                                <div class="table_value_template management_table_menu_template system_notice_notificationType">
                                    <c:out value="${notification.notificationType}"/>
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
                                                <a href="/admin/notices?page=${currentPage-5}&size=8&sort=id,asc" aria-label="Previous">
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
                                                    <a href="/admin/notices?page=${p}&size=8&sort=id,asc">
                                                        <c:out value="${p+1}" />
                                                    </a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li>
                                                    <a href="/admin/notices?page=${p}&size=8&sort=id,asc">
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
                                                <a href="/admin/notices?page=${pages}&size=8&sort=id,asc" aria-label="Next">
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


<div class="write_notification_wrapper hoflearn_modal_wrapper">
    <div class="write_notification_form hoflearn_modal_form animate">
        <form class="notification_form" action="/admin/add/notice" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="hoflearn_modal_relative_wrap">
                <div class="hoflearn_modal_content_template">
                    <div class="modal_title hoflearn_modal_menu_template">시스템 공지사항</div>
                    <div class="hoflearn_modal_menu_template">공지사항 제목</div>
                    <div class="hoflearn_modal_input_template">
                        <input type="text" class="question_name" name="title" placeholder="제목을 입력해주세요.">
                    </div>
                </div>
                <div class="hoflearn_modal_content_template">
                    <div class="hoflearn_modal_menu_template">알림 내용</div>
                    <textarea id="notificationContent" name="content"></textarea>
                </div>
            </div>
            <div class="hoflearn_modal_button_wrap">
                <div class="hoflearn_modal_button_template notification_cancel">취소</div>
                <div class="hoflearn_modal_button_template notification_submit">발송</div>
            </div>
        </form>
    </div>
</div>

<div class="write_success_wrapper hoflearn_modal_result_wrapper">
    <div class="write_notification_form hoflearn_modal_result_form animate">
        <div class="hoflearn_modal_relative_wrap">
            <div class="hoflearn_modal_content_template">
                <div class="hoflearn_modal_icon_template">
                    <i class="far fa-check-circle"></i>
                </div>
                <div class="hoflearn_modal_menu_title_template">공지사항 발송 완료</div>
            </div>
        </div>
        <div class="hoflearn_modal_button_wrap">
            <div class="hoflearn_modal_button_template write_notification_button">확인</div>
        </div>
    </div>
</div>

<c:if test="${modal ne null}">
    <c:choose>
        <c:when test="${modal eq 'System'}">
            <script>
                openModal(".write_success_wrapper");
            </script>
        </c:when>
    </c:choose>
</c:if>