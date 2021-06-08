<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-08
  Time: 오전 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<link rel="stylesheet" href="/css/views/md/menu/instructorInfo.css">
<script src="/js/views/md/menu/instructorInfo.js"></script>

<div class="main_wrap">
    <div class="main_flex_wrap">
        <div class="card_wrap">
            <div class="member_profile_warp">
                <div class="profile_image_flex_wrap">
                    <div class="profile_image_wrap">
                        <img src="${member.profile}" class="profile_image_value">
                    </div>
                </div>
                <div class="profile_text_wrap">
                    <div class="profile_flex_items">
                        <div class="profile_flex_item">
                            <div class="profile_username_wrap profile_flex_template">
                                <div class="profile_menu_wrap">아이디</div>
                                <div class="profile_value_wrap"><c:out value="${member.username}"/></div>
                            </div>
                            <div class="profile_email_wrap profile_flex_template">
                                <div class="profile_menu_wrap">이메일</div>
                                <div class="profile_value_wrap"><c:out value="${member.email}"/></div>
                            </div>
                            <div class="profile_nickName_wrap profile_flex_template">
                                <div class="profile_menu_wrap">닉네임</div>
                                <div class="profile_value_wrap"><c:out value="${member.nickName}"/></div>
                            </div>
                            <div class="profile_memberName_wrap profile_flex_template">
                                <div class="profile_menu_wrap">이름(실명)</div>
                                <div class="profile_value_wrap"><c:out value="${member.memberName}"/></div>
                            </div>
                        </div>
                        <div class="profile_flex_item">
                            <div class="profile_tel_wrap profile_flex_template">
                                <div class="profile_menu_wrap">전화번호</div>
                                <div class="profile_value_wrap"><c:out value="${member.tel}"/></div>
                            </div>
                            <div class="profile_courseCNT_wrap profile_flex_template">
                                <div class="profile_menu_wrap">강의 수</div>
                                <div class="profile_value_wrap"><c:out value="${member.instructor.totalCourseCount()}"/>
                                    개
                                </div>
                            </div>
                            <div class="profile_totalRevenue_wrap profile_flex_template">
                                <div class="profile_menu_wrap">총 수익</div>
                                <div class="profile_value_wrap">0 원</div>
                            </div>
                            <div class="profile_createAt_wrap profile_flex_template">
                                <div class="profile_menu_wrap">가입일</div>
                                <div class="profile_value_wrap profile_member_createAt profile_member_createAt_${member.id}">
                                    <script>
                                        convertLocalDateTime('${member.create_at}', '.profile_member_createAt_${member.id}')
                                    </script>
                                </div>
                            </div>
                        </div>
                        <div class="profile_flex_item">
                            <div class="profile_tel_wrap profile_flex_template">
                                <div class="profile_menu_wrap">강사소개</div>
                                <c:choose>
                                    <c:when test="${member.selfIntroduce ne null}">
                                        <div class="profile_value_wrap selfIntroduce_${member.id}">
                                            <script>
                                                removeHtmlTag('${member.selfIntroduce}', '.selfIntroduce_${member.id}');
                                            </script>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="profile_value_wrap">
                                            등록된 강사소개가 없어요!
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="profile_toolbox">
                        <div class="writeNotification profile_button_template">알림 메시지 보내기</div>
                        <div class="writeMail profile_button_template">메일 보내기</div>
                    </div>
                </div>
            </div>
        </div>
        <c:choose>
            <c:when test="${fn:length(member.instructor.courses) gt 0}">
                <c:forEach var="course" items="${member.instructor.courses}" varStatus="index">
                    <div class="card_wrap">
                        <div class="instructor_course_wrap">
                            <div class="instructor_course_flex_space_between_wrap">
                                <div class="instructor_course_title_wrap">
                                    <div class="highlight">
                                        <c:out value="${member.nickName}"/>
                                    </div>
                                    <div class="nonHighlight">님이 업로드 하신 강의</div>
                                    <div class="highlight">
                                        [<c:out value="${course.name}"/>]
                                    </div>
                                    <div class="nonHighlight"></div>
                                </div>
                                <div class="instructor_course_button_wrap">
                                    <div class="preview instructor_course_button_template" data-url="${course.url}">강의
                                        미리보기
                                    </div>
                                    <div class="manage instructor_course_button_template" data-id="${course.id}">강의 관리
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="card_wrap">
                    강의가 없어요!
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="write_notification_wrapper hoflearn_modal_wrapper">
    <div class="write_notification_form hoflearn_modal_form animate">
        <form class="notification_form" action="/md/add/notification" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="member_id" value="${member.id}"/>
            <div class="hoflearn_modal_relative_wrap">
                <div class="hoflearn_modal_content_template">
                    <div class="hoflearn_modal_menu_template">알림 제목</div>
                    <div class="hoflearn_modal_input_template">
                        <input type="text" class="question_name" name="title" placeholder="제목을 입력해주세요.">
                    </div>
                </div>
                <div class="hoflearn_modal_content_template">
                    <div class="hoflearn_modal_menu_template">알림 타입</div>
                    <div class="notification_template_wrap">
                        <input type="hidden" name="notificationType" class="notificationType"/>
                        <c:forEach var="notification" items="${notifications}" varStatus="index">
                            <div class="notification_template" data-value="${notification.value}">
                                <c:out value="${notification.value}"/>
                            </div>
                        </c:forEach>
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
                <div class="hoflearn_modal_menu_title_template">메세지 발송 완료</div>
            </div>
        </div>
        <div class="hoflearn_modal_button_wrap">
            <div class="hoflearn_modal_button_template write_notification_button">확인</div>
        </div>
    </div>
</div>

<c:if test="${modal ne null}">
    <script>
        openModal('.write_success_wrapper');
    </script>
</c:if>