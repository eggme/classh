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

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/views/instructor/courseQnA.css"/>
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
                    <div class="username_value">
                        <c:out value="${member.nickName}"/>
                    </div>
                    <div class="modify_at_wrap">
                        <div class="separator">&nbsp;·&nbsp;</div>
                        <div class="modify_at_value">
                            <script>
                                convertLocalDateTime('${courseQuestion.modify_at}', '.modify_at_value');
                            </script>
                        </div>
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
                                    <div class="course_link link_template" data-id="${course.url}">
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
                    <c:out value="${courseQuestion.questionStatus.getValue()}"/>
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
                                    <div class="answer_profile_modify_at">
                                        <script>
                                            convertLocalDateTime('${commentMember.modify_at}', '.answer_profile_modify_at');
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
                                </div>
                            </div>
                        </div>
                        <div class="reply_box">
                            <div class="reply_menu_box">
                                <div class="reply_title_menu">댓글</div>
                                <div class="replay_toggle_button">
                                    <div class="open_button">더보기 <i class="fas fa-chevron-up"></i></div>
                                        <%--                            <div class="hide_button">접기<i class="fas fa-chevron-down"></i></div>--%>
                                </div>
                            </div>
                            <div class="reply_add_box">
                                <div class="write_reply">댓글 달기</div>
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


<div class="question_write_form_wrapper" data-id="${course.id}">
    <div class="question_write_form animate">
        <form class="question_form" action="/question/add" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="course_id" class="course_id" />
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
                <div class="question_content_wrap question_wrap_template">
                    <div class="question_content_menu question_menu_template">질문 내용</div>
                    <div class="question_content_input">
                <textarea id="myQuestion" name="content">
                    <p><b>강의와 관련있는 질문을 남겨주세요.</b></p>
<p>• 강의와 관련이 없는 질문은 지식공유자가 답변하지 않을 수 있습니다. (사적 상담, 컨설팅, 과제 풀이 등)</p>
<p>• 질문을 남기기 전, 비슷한 내용을 질문한 수강생이 있는지 먼저 검색을 해주세요. (중복 질문을 자제해주세요.)</p>
<p>• <u>서비스 운영 관련 질문은 인프런 우측 하단 ‘문의하기’</u>를 이용해주세요. (영상 재생 문제, 사이트 버그, 강의 환불 등)</p>
<br/>
<p><b>질문 전달에도 요령이 필요합니다.</b></p>
<p>• 지식공유자가 질문을 좀 더 쉽게 확인할 수 있게 도와주세요.</p>
<p>• 강의실 페이지(/lecture) 에서 '질문하기'를 이용해주시면 질문과 연관된 수업 영상 제목이 함께 등록됩니다.</p>
<p>• 강의 대시보드에서 질문을 남길 경우, <u>관련 섹션 및 수업 제목을 기재</u>해주세요.</p>
<p>• 수업 특정 구간에 대한 질문은 꼭 <u>영상 타임코드</u>를 남겨주세요!</p>
<br/>
<p><b>구체적인 질문일수록 명확한 답을 받을 수 있어요.</b></p>
<p>• 질문 제목은 핵심 키워드를 포함해 간결하게 적어주세요.</p>
<p>• 질문 내용은 자세하게 적어주시되, 지식공유자가 답변할 수 있도록 <u>구체적으로 남겨주세요.</u></p>
<p>• 정확한 질문 내용과 함께 코드를 적어주시거나, <u>캡쳐 이미지</u>를 첨부하면 더욱 좋습니다.</p>
<br/>
<p><b>기본적인 예의를 지켜주세요.</b></p>
<p>• 정중한 의견 및 문의 제시, 감사 인사 등의 커뮤니케이션은 더 나은 강의를 위한 기틀이 됩니다.</p>
<p>• 질문이 있을 때에는 강의를 만든 지식공유자에 대한 기본적인 예의를 꼭 지켜주세요.</p>
<p>• 반말, 욕설, 과격한 표현 등 지식공유자를 불쾌하게 할 수 있는 내용은 스팸 처리 등 제재를 가할 수 있습니다.</p> </textarea>
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