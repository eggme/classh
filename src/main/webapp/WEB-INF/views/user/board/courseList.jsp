<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-26
  Time: 오후 1:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/css/views/user/courseList.css" />
<script src="/js/views/user/courseList.js"></script>

<div class="main_wrap">
    <div class="container_wrap">
        <div class="content_wrap">
            <div class="flex_content_wrap">
                <div class="menu_title">
                    <span class="font_size_10">내 학습 / 내 강의</span>
                    <span class="title_menu">내 강의</span>
                </div>
                <div class="row">
                    <c:set var="histories" value="${courseHistory}" />
                    <c:choose>
                        <c:when test="${fn:length(list) gt 0}">
                            <c:forEach var="course" items="${list}" varStatus="index">

                                <c:set var="total_section_class_count" value="0"/>
                                <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                                    <c:forEach var="class_o" items="${section.courseClasses}" varStatus="status">
                                        <c:set var="total_section_class_count" value="${total_section_class_count + 1}"/>
                                    </c:forEach>
                                </c:forEach>

                                <c:set var="totalTime" value="${course.getTotalTime()}"></c:set>

                                <div class="col-md-4">
                                    <div class="course_wrap" data-id="${course.id}">
                                        <div class="course_img_wrap">
                                            <img class="my_course_img" src="${course.courseImg}"/>
                                        </div>
                                        <div class="course_title_wrap">
                                            <span class="course_title" data-id="${course.id}">
                                                <c:out value="${course.name}"/>
                                            </span>
                                            <div class="course_play"><i class="far fa-play-circle"></i></div>
                                        </div>
                                        <div class="course_progress_wrap">
                                            <progress class="course_progress course_progress_${course.id}" value="0" max="100"/>
                                        </div>
                                        <div class="course_tool_box">
                                            <div class="progress_text">
                                                <span>진행률 : </span>
                                                <span class="progress_value_wrap progress_value_wrap_${course.id}">
                                                    <script>
                                                        console.log(${histories.findHistoriesCompletionCourseCount(course)});
                                                        console.log(${total_section_class_count});
                                                        getPercent('${histories.findHistoriesCompletionCourseCount(course)}',
                                                            '${total_section_class_count}',
                                                            '.progress_value_wrap_${course.id}');
                                                    </script>
                                                </span>
                                                <span>&nbsp; | &nbsp;기한 : 무제한</span>
                                                <c:if test="${!(histories eq null)}">
                                                    <script>
                                                        getPercentProgress('${histories.findHistoriesCompletionCourseCount(course)}', '${total_section_class_count}', '.course_progress_${course.id}');
                                                    </script>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="nosearch">
                                <p class="highlight">🙈 아직 강의가 없습니다! 🙊</p>
                                <p>필터를 다시 적용해보시거나 새로운 강의를 학습해 보세요!</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>