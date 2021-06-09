<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오후 2:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/css/component/description.css">

<c:set var="total_section_class_count" value="0"/>
<c:forEach var="section" items="${course.courseSections}" varStatus="status">
    <c:forEach var="class_o" items="${section.courseClasses}" varStatus="status">
        <c:set var="total_section_class_count" value="${total_section_class_count + 1}"/>
    </c:forEach>
</c:forEach>

<div class="description_wrap">
    <div class="flex_content">
        <div class="course_thumbnail">
            <img class="course_img" src="${course.courseImg}"/>
            <div class="video_box">
                <div class="play_button">
                    <div class="play_icon"></div>
                </div>
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication var="userobject" property="principal" />
                    <c:if test="${course.isCourseRegistration(userobject)}">
                        <c:if test="${!(courseHistory eq null)}">
                            <div class="play_text">
                                <div class="play_text_title">이어 학습하기</div>
                                <div class="play_text_percent">진도율 :
                                    <c:out value="${courseHistory.findHistoriesCompletionCourseCount(course)}"/>강 / <c:out
                                            value="${total_section_class_count}"/>강 (<span
                                            class="course_current_percent_value_area">
                                            <script>getPercent('${courseHistory.findHistoriesCompletionCourseCount(course)}', '${total_section_class_count}', '.course_current_percent_value_area')</script></span>)
                                </div>
                            </div>
                            <div class="video_progress">
                                <div class="progress_wrap">
                                    <progress class="course_progress_data" value="80" max="100"/>
                                    <script>
                                        getPercentProgress('${courseHistory.findHistoriesCompletionCourseCount(course)}', '${total_section_class_count}', '.course_progress_data');
                                    </script>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                </sec:authorize>
            </div>
        </div>
        <div class="flex_column_wrap">
            <div class="content_text">
                <div class="content_title"><c:out value="${course.name}"></c:out></div>
                <div class="content_info">
                    <div class="student_count margin_bottom_10">
                        <span class="icon"><i class="far fa-eye"></i></span>
                        <span class="total_student_count">${fn:length(course.signUpCourses)}</span>
                        <span class="total_student_count_text">명이 수강하고 있어요!</span>
                    </div>
                    <div class="instructor_information">
                        <span class="icon"><i class="fas fa-chalkboard-teacher"></i></span>
                        <span class="instructor_name"><c:out
                                value="${course.instructor.member.nickName}"></c:out></span>
                        <span class="after_icon size12"><i class="fas fa-crown"></i></span>
                    </div>
                    <div class="skill_tag">
                        <div class="icon_div icon"><i class="fas fa-hashtag"></i></div>
                        <div class="tag_wrap">
                            <c:if test="${course.courseTags ne null}">
                                <c:if test="${fn:length(course.courseTags) gt 0}">
                                    <c:forEach var="tag" items="${course.courseTags}" varStatus="idx">
                                        <div class="tag"><c:out value="${tag.tag}"/></div>
                                    </c:forEach>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="button_group">
                        <div class="add_list_box while_line_box">
                            <i class="far fa-plus-square line_height"></i> 내 목록 추가
                        </div>
                        <div class="share_box while_line_box">
                            <i class="fas fa-share-alt line_height"></i> 공유하기
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>