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

<link rel="stylesheet" href="/css/views/instructor/courseQnA.css" />
<div class="main_section">
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
                    <c:out value="${courseQuestion.title}" />
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
                <c:out value="${courseQuestion.questionStatus.getValue()}" />
            </div>
        </div>
    </div>
</div>