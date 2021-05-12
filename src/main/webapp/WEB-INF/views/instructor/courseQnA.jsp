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
            <div class="back_button">
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
                            <%--                        <c:choose>--%>
                            <%--                            <c:when test="${!course eq null}">--%>
                            <%--                                <c:out value="${course.name}"></c:out>--%>
                            <%--                            </c:when>--%>
                            <%--                            <c:when test="${!courseClass eq null}">--%>
                            <%--                                <c:out value="${courseClass.name}"></c:out>--%>
                            <%--                            </c:when>--%>
                            <%--                        </c:choose>--%>
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
                    총 <span class="answer_title_value">1</span>개의 답변이 달렸습니다
                </div>
            </div>
        </div>
        <div class="answer_main_box_wrap">
            <div class="answer_size_wrap">
                <%-- 반복 --%>
                <div class="answer_template">
                    <div class="answer_box">
                        <div class="answer_profile_wrap">
                            <div class="answer_profile_image_wrap">
                                <img class="answer_profile_image" src="/imgs/mini_icon_1.png"/>
                            </div>
                            <div class="answer_profile_data_wrap">
                                <div class="answer_profile_username">정수원</div>
                                <div class="answer_profile_modify_at">2021년 11월 11일</div>
                            </div>
                        </div>
                        <div class="answer_content_wrap">
                            <div class="answer_content">
                                혹시 인텔리제이에서 실행시킬 때 JDK 를 선택해 주셨나요?<br/>

                                인텔리제이에서 프로젝트 세팅에 가 보시면 JDK 를 선택할 수 있는데 확인해 보시기 바랍니다<br/>

                                위 오류는 인텔리제이와 관련된 것이나 JDK 설정 관련된 오류로 보입니다.<br/>
                                정확한 원인은 실제 개발 환경에서만 파악이 가능한 부분이고 저의 시스템에서 재현 하기가 어려워 해결에 대한 답변을 드리지 못하는점 양해 부탁드립니다.
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
                <div class="answer_template">
                    <div class="answer_box">
                        <div class="answer_profile_wrap">
                            <div class="answer_profile_image_wrap">
                                <img class="answer_profile_image" src="/imgs/mini_icon_1.png"/>
                            </div>
                            <div class="answer_profile_data_wrap">
                                <div class="answer_profile_username">정수원</div>
                                <div class="answer_profile_modify_at">2021년 11월 11일</div>
                            </div>
                        </div>
                        <div class="answer_content_wrap">
                            <div class="answer_content">
                                혹시 인텔리제이에서 실행시킬 때 JDK 를 선택해 주셨나요?<br/>

                                인텔리제이에서 프로젝트 세팅에 가 보시면 JDK 를 선택할 수 있는데 확인해 보시기 바랍니다<br/>

                                위 오류는 인텔리제이와 관련된 것이나 JDK 설정 관련된 오류로 보입니다.<br/>
                                정확한 원인은 실제 개발 환경에서만 파악이 가능한 부분이고 저의 시스템에서 재현 하기가 어려워 해결에 대한 답변을 드리지 못하는점 양해 부탁드립니다.
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
                <div class="answer_template">
                    <div class="answer_box">
                        <div class="answer_profile_wrap">
                            <div class="answer_profile_image_wrap">
                                <img class="answer_profile_image" src="/imgs/mini_icon_1.png"/>
                            </div>
                            <div class="answer_profile_data_wrap">
                                <div class="answer_profile_username">정수원</div>
                                <div class="answer_profile_modify_at">2021년 11월 11일</div>
                            </div>
                        </div>
                        <div class="answer_content_wrap">
                            <div class="answer_content">
                                혹시 인텔리제이에서 실행시킬 때 JDK 를 선택해 주셨나요?<br/>

                                인텔리제이에서 프로젝트 세팅에 가 보시면 JDK 를 선택할 수 있는데 확인해 보시기 바랍니다<br/>

                                위 오류는 인텔리제이와 관련된 것이나 JDK 설정 관련된 오류로 보입니다.<br/>
                                정확한 원인은 실제 개발 환경에서만 파악이 가능한 부분이고 저의 시스템에서 재현 하기가 어려워 해결에 대한 답변을 드리지 못하는점 양해 부탁드립니다.
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
                <%-- 반복 --%>
                <div class="user_answer_wrap">
                    <sec:authorize access="isAuthenticated()">
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
                            <textarea class="user_answer"></textarea>
                        </div>
                        <div class="user_answer_submit_wrap">
                            <div class="user_answer_submit">
                                답변 등록
                            </div>
                        </div>
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
