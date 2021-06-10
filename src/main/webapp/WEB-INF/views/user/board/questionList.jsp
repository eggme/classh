<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-10
  Time: 오후 4:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/css/views/user/questionList.css">
<script src="/js/views/user/questionList.js"></script>

<div class="main_wrap">
    <div class="main_flex_wrap">
        <div class="question_filter_box_wrap">
            <div class="question_filter_box_title">내 질문</div>
        </div>
        <div class="question_content_box_wrap">
            <div class="question_status_box_wrap">
                <div class="question_status_all_wrap checked question_status_template">
                    <div class="question_status_icon_template"></div>
                    <div class="question_status_all question_status_value_template">전체</div>
                </div>
                <div class="question_status_complete_wrap question_status_template">
                    <div class="question_status_icon_template"></div>
                    <div class="question_status_complete question_status_value_template">해결</div>
                </div>
                <div class="question_status_nonComplete_wrap question_status_template">
                    <div class="question_status_icon_template"></div>
                    <div class="question_status_nonComplete question_status_value_template">미해결</div>
                </div>
            </div>
            <div class="question_content_box">
                <c:forEach var="question" items="${list}" varStatus="question_status">
                    <div class="question_content" data-url="/question/${question.id}">
                        <div class="question_data_area">
                            <div class="question_content_wrap">
                                <div class="review_title">
                                    <c:out value="${question.title}"/>
                                </div>
                                <div class="review_content">
                                        ${question.content}
                                </div>
                                <div class="review_tags">
                                    <c:forEach var="tags" items="${question.courseTags}" varStatus="tag_status">
                                        <div class='review_hash_tag'>
                                        <span class='review_tag_value'>#
                                            <c:out value="${tags.tag}"></c:out>
                                        </span>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="question_member_wrap">
                                <div class="question_data_wrap">
                                    <div class="member_name">
                                        <c:out value="${question.member.nickName}"/>
                                    </div>
                                    <div class="separator">&nbsp;·&nbsp;</div>
                                    <div class="question_time question_${question.id}">
                                        <script>
                                            timeForTodayObject("${question.modify_at}", '.question_${question.id}');
                                        </script>
                                    </div>
                                    <div class="separator">&nbsp;·&nbsp;</div>
                                    <div class="course_title">
                                        <c:out value="${question.course.name}"/>
                                        <c:if test="${!question.courseClass eq null}">
                                            <div class="separator">&nbsp;·&nbsp;</div>
                                            <div class="courseClass_title">
                                                <c:out value="${question.courseClass.name}"/>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="question_toolbox_wrap">
                                    <div class="question_toolbox_bookmark_wrap question_toolbox_template">
                                        <div class="question_toolbox_icon">
                                            <i class="far fa-bookmark"></i>
                                        </div>
                                        <div class="question_toolbox_count">0</div>
                                    </div>
                                    <div class="question_toolbox_like_wrap question_toolbox_template">
                                        <div class="question_toolbox_icon">
                                            <i class="far fa-heart"></i>
                                        </div>
                                        <div class="question_toolbox_count">0</div>
                                    </div>
                                    <div class="question_toolbox_comment_wrap question_toolbox_template">
                                        <div class="question_toolbox_icon">
                                            <i class="far fa-comment-alt"></i>
                                        </div>
                                        <div class="question_toolbox_count">0</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                            <%--                        <div class="question_toolbar">--%>
                            <%--                            <div class="circle_answer_area">--%>
                            <%--                                <div class="answer_count">--%>
                            <%--                                    <c:out value="${question.getCommentSize()}"/>--%>
                            <%--                                </div>--%>
                            <%--                                <div class="answer_title">--%>
                            <%--                                    <c:out value="${question.questionStatus.getValue()}"/>--%>
                            <%--                                </div>--%>
                            <%--                            </div>--%>
                            <%--                            <div class="like_area">--%>
                            <%--                                <div class="like_icon"><i class="fas fa-heart"></i></div>--%>
                            <%--                                <div class="like_count">0</div>--%>
                            <%--                            </div>--%>
                            <%--                        </div>--%>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>