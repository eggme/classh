<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-11
  Time: 오후 2:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/css/views/instructor/courseQnA.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<script src="/js/views/instructor/courseQnA.js"></script>

<div class="main_section">
    <div class="question_box_wrap">
        <div class="left_side_wrap">
            <div class="back_button" data-id="${course.id}">
                <i class="fas fa-arrow-left"></i>
            </div>
        </div>
        <div class="question_wrap">
            <div class="question_title_wrap">
                <div class="question_title">
                    <div class="title_menu">Q</div>
                    <div class="title_value">
                        <c:out value="${courseQuestion.title}"/>
                    </div>
                </div>
                <div class="question_user">
                    <div class="question_user_wrap">
                        <div class="username_value">
                        <c:out value="${member.nickName}"/>
                    </div>
                        <div class="modify_at_wrap">
                        <div class="separator">&nbsp;·&nbsp;</div>
                        <div class="modify_at_value courseQuestion_${courseQuestion.id}">
                            <script>
                                convertLocalDateTime('${courseQuestion.modify_at}', '.courseQuestion_${courseQuestion.id}');
                            </script>
                        </div>
                    </div>
                    </div>
                    <div class="question_user_toolbox_wrap">
                        <sec:authorize access="isAuthenticated()">
                            <sec:authentication property="principal.id" var="user_id"/>
                            <c:if test="${courseQuestion.member.id eq user_id}">
                                <div class="question_edit_box" data-id="${courseQuestion.id}">수정</div>
                                <div class="question_delete_box" data-id="${courseQuestion.id}">삭제</div>
                            </c:if>
                        </sec:authorize>
                    </div>
                </div>
            </div>
            <div class="question_content_wrap">
                <div class="question_content">
                    ${courseQuestion.content}
                </div>
                <div class="question_data_box">
                    <div class="course_data_wrap">
                        <div class="course_data">연관 강의</div>
                        <div class="course_data_value">
                            <c:choose>
                                <c:when test="${!(course eq null)}">
                                    <div class="course_link link_template" data-id="${course.id}">
                                        <c:out value="${course.name}"></c:out>
                                    </div>
                                </c:when>
                                <c:when test="${!(courseClass eq null)}">
                                    <div class="separator"> > </div>
                                    <div class="courseClass_link link_template" data-id="${courseClass.id}">
                                        <c:out value="${courseClass.name}"></c:out>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                    <div class="course_tag_data_wrap">
                        <div class="course_tag">연관 태그</div>
                        <div class="course_tag_value">
                            <c:forEach var="tags" items="${courseQuestion.courseTags}" varStatus="tag_status">
                                <div class='review_hash_tag'>
                                            <span class='review_tag_value'>#
                                                <c:out value="${tags.tag}"></c:out>
                                            </span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="right_side_wrap">
            <div class="tool_box_wrap">
                <div class="tool_box question_status">
                    <script>
                        resolvedClass('${courseQuestion.questionStatus.getValue()}', '.question_status');
                    </script>
                    <sec:authorize access="isAuthenticated()">
                        <sec:authentication property="principal.id" var="user_id"/>
                        <c:choose>
                            <c:when test="${courseQuestion.member.id eq user_id}">
                                <div class="isMeWrote" data-id="1" data-qid="${courseQuestion.id}"></div>
                            </c:when>
                            <c:otherwise>
                                <div class="isMeWrote" data-id="0"></div>
                            </c:otherwise>
                        </c:choose>
                    </sec:authorize>
                </div>
                <div class="tool_box like_box">
                    <div class="like_icon">
                        <i class="far fa-heart"></i>
                        <%-- 색칠된 하트 --%>
                        <%--<i class="fas fa-heart"></i>--%>
                    </div>
                    <div class="like_count">0</div>
                </div>
            </div>
        </div>
    </div>
    <div class="answer_box_wrap">
        <div class="answer_main_text_wrap">
            <div class="answer_main_text_size_wrap">
                <div class="answer_title_menu">A</div>
                <div class="answer_title_value_area">
                    총 <span class="answer_title_value"><c:out value="${courseQuestion.getCommentSize()}"/></span>개의 답변이
                    달렸습니다
                </div>
            </div>
        </div>
        <div class="answer_main_box_wrap">
            <div class="answer_size_wrap">
                <%-- 반복 --%>
                <c:forEach var="comment" items="${list}" varStatus="comment_status">
                    <c:set var="commentMember" value="${comment.member}"/>
                    <div class="answer_template">
                        <div class="answer_box">
                            <div class="answer_profile_wrap">
                                <div class="answer_profile_image_wrap">
                                    <img class="answer_profile_image" src="${commentMember.profile}"/>
                                </div>
                                <div class="answer_profile_data_wrap">
                                    <div class="answer_profile_username">
                                        <c:out value="${commentMember.nickName}"/>
                                    </div>
                                    <div class="answer_profile_modify_at comment_${comment_id}">
                                        <script>
                                            convertLocalDateTime('${comment.modify_at}', '.comment_${comment_id}');
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="answer_content_wrap">
                                <div class="answer_content">
                                        ${comment.commentContent}
                                </div>
                                <div class="answer_content_toolbox">
                                    <div class="like_box_wrap">
                                        <div class="like_icon">
                                            <i class="far fa-heart"></i>
                                                <%-- 색칠된 하트 --%>
                                                <%--<i class="fas fa-heart"></i>--%>
                                        </div>
                                        <div class="like_count">0</div>
                                    </div>
                                    <div class="answer_tool_box">
                                        <sec:authorize access="isAuthenticated()">
                                            <sec:authentication property="principal.id" var="user_id"/>
                                            <c:if test="${courseQuestion.member.id eq user_id}">
                                                <div class="answer_edit_wrap" data-id="${comment.id}">수정</div>
                                                <div class="answer_delete_wrap" data-id="${comment.id}">삭제</div>
                                            </c:if>
                                        </sec:authorize>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="reply_box">
                            <div class="reply_menu_box">
                                <div class="reply_title_menu">댓글</div>
                                <div class="replay_toggle_button">
                                    <div class="open_button">접기 <i class="fas fa-chevron-up"></i></div>
                                <%--<div class="hide_button">더보기 <i class="fas fa-chevron-down"></i></div>--%>
                                </div>
                            </div>
                            <div class="reply_list_wrap">
                                <c:set var="reply_wrap" value="${comment.children}"></c:set>
                                <c:if test="${!empty reply_wrap}">
                                    <c:forEach var="reply" items="${reply_wrap}" varStatus="reply_st">
                                        <div class="reply_template">
                                            <div class="reply_image_box">
                                                <div class="reply_image_wrap">
                                                    <img src="${reply.member.profile}" class="reply_image" />
                                                </div>
                                                <div class="reply_image_line"></div>
                                            </div>
                                            <div class="reply_content_box">
                                                <div class="reply_user_profile">
                                                    <c:if test="${reply.member.id eq courseQuestion.member.id}">
                                                        <div class="reply_additional_box">질문자</div>
                                                    </c:if>
                                                    <div class="reply_username_box">
                                                        <c:out value="${reply.member.nickName}"></c:out>
                                                    </div>
                                                    <div class="separator">·</div>
                                                    <div class="reply_modify_at reply_${reply.id}">
                                                        <script>
                                                            convertLocalDateTime('${reply.modify_at}', '.reply_${reply.id}');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="reply_user_content">
                                                        ${reply.commentContent}
                                                </div>
                                                <sec:authorize access="isAuthenticated()">
                                                    <sec:authentication var="auth" property="principal.id"></sec:authentication>
                                                    <c:if test="${reply.member.id eq auth}">
                                                        <div class="reply_user_toolbox">
                                                            <div class="reply_user_edit" data-id="${reply.id}">수정</div>
                                                            <div class="reply_user_delete" data-id="${reply.id}">삭제</div>
                                                        </div>
                                                    </c:if>
                                                </sec:authorize>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <div class="reply_add_box">
                                <div class="write_reply" data-id="${comment.id}" data-qid="${courseQuestion.id}">댓글 달기</div>
                                <div class="write_reply_form_wrap hidden">
                                    <div class="write_reply_form">
                                        <input type="hidden" name="comment_id" class="comment_id" />
                                        <input type="hidden" name="question_id" class="question_id" />
                                        <textarea name="commentContent" class="comment_reply" id="tiny${comment.id}"></textarea>
                                        <div class="write_reply_button_wrap">
                                            <div class="write_reply_submit">답변 등록</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <%-- 반복 --%>
                <div class="user_answer_wrap">
                    <sec:authorize access="isAuthenticated()">
                        <form class="user_answer_form" action="/question/add/comment" method="post"
                              data-qid="${courseQuestion.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="q_id" class="q_id"/>
                            <div class="user_answer_profile_wrap">
                                <div class="user_answer_profile_image_wrap">
                                    <img class="user_answer_profile_image" src="/imgs/mini_icon_1.png"/>
                                </div>
                                <div class="user_answer_profile_data_wrap">
                                    <div class="user_answer_profile_username">
                                        <span class="username">
                                            <sec:authentication property="principal.nickName"/>
                                        </span>
                                        <span class="username_template">님, 답변해주세요!</span>
                                    </div>
                                    <div class="user_answer_profile_template">모두에게 도움이 되는 답변의 주인공이 되어주세요!</div>
                                </div>
                            </div>
                            <div class="user_answer_textarea">
                                <textarea class="user_answer" name="commentContent"></textarea>
                            </div>
                            <div class="user_answer_submit_wrap">
                                <div class="user_answer_submit">
                                    답변 등록
                                </div>
                            </div>
                        </form>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <div class="anonymous_user">
                            로그인 후, 질문 답변 작성이 가능합니다.
                        </div>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="question_write_form_wrapper modal_form_wrapper" data-id="">
    <div class="question_write_form modal_form animate">
        <form class="question_edit_form" action="/question/edit" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" class="q_id" />
            <div class="question_relative_wrap">
                <div class="question_name_wrap question_wrap_template">
                    <div class="question_name_menu question_menu_template">제목</div>
                    <div class="question_name_input question_input_template">
                        <input type="text" class="question_name" name="title" placeholder="제목을 입력해주세요.">
                    </div>
                </div>
                <div class="question_tag_wrap question_wrap_template">
                    <div class="question_tag_menu question_menu_template">태그</div>
                    <div class="question_tag_input">
                        <div class="tag_wrap_template">
                            <div class="hashtag"><i class="fas fa-hashtag"></i></div>
                            <div class="hashtag_value">

                            </div>
                            <input type="text" class="question_tag" placeholder="태그를 설정해주세요.">
                        </div>
                        <div class="tag_description">최대 10개의 태그를 달 수 있어요!</div>
                    </div>
                </div>
                <div class="question_wrap_template">
                    <div class="question_content_menu question_menu_template">질문 내용</div>
                    <div class="question_content_input">
                <textarea id="myQuestion" name="content"></textarea>
                    </div>
                </div>
            </div>
            <div class="question_button_wrap">
                <div class="question_button_template question_cancel">취소</div>
                <div class="question_button_template question_submit">저장</div>
            </div>
        </form>
    </div>
</div>

<div class="question_delete_form_wrapper modal_form_wrapper" data-id="">
    <div class="question_delete_wrapper modal_form animate">
        <form class="question_delete_form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" class="q_id" />
            <div class="question_delete_icon_wrap">
                <div class="question_delete_icon">
                    <i class="fas fa-exclamation-triangle"></i>
                </div>
            </div>
            <div class="question_delete_text">
                <div class="question_delete_title">글 삭제</div>
                <div class="question_delete_content">해당 글을 삭제하시겠습니까?</div>
            </div>
            <div class="question_delete_button_area">
                <div class="question_delete_cancel">취소</div>
                <div class="question_delete_submit">삭제</div>
            </div>
        </form>
    </div>
</div>

<div class="comment_delete_form_wrapper modal_form_wrapper" data-id="">
    <div class="comment_delete_wrapper modal_form animate">
        <form class="comment_delete_form" action="/question/comment/delete" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" class="c_id" />
            <div class="comment_delete_icon_wrap">
                <div class="comment_delete_icon">
                    <i class="fas fa-exclamation-triangle"></i>
                </div>
            </div>
            <div class="comment_delete_text">
                <div class="comment_delete_title">답변 삭제</div>
                <div class="comment_delete_content">해당 답변을 삭제하시겠습니까?</div>
            </div>
            <div class="comment_delete_button_area">
                <div class="comment_delete_cancel">취소</div>
                <div class="comment_delete_submit">삭제</div>
            </div>
        </form>
    </div>
</div>

<div class="comment_edit_form_wrapper modal_form_wrapper" data-id="">
    <div class="comment_edit_wrapper modal_form animate">
        <form class="comment_edit_form" action="/question/comment/edit" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" class="c_id" />
            <div class="comment_edit_title_wrap">
                <div class="comment_edit_title">답변 수정</div>
            </div>
            <div class="comment_edit_content_wrap">
                <textarea class="comment_edit_content" name="commentContent" id="myComment"></textarea>
            </div>
            <div class="comment_edit_button_area">
                <div class="comment_edit_cancel">취소</div>
                <div class="comment_edit_submit">저장</div>
            </div>
        </form>
    </div>
</div>