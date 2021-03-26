<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-25
  Time: 오후 3:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link href="https://vjs.zencdn.net/7.8.4/video-js.css" rel="stylesheet"/>
<script src="https://vjs.zencdn.net/ie8/1.1.2/videojs-ie8.min.js"></script>
<script src="https://vjs.zencdn.net/7.8.4/video.js"></script>
<link rel="stylesheet" href="/css/studyRoom.css">
<script src="/js/studyRoom.js"></script>
<div class="flex_container">
    <div class="study_wrap">
        <div class="curriculum_wrap actived">
            <div class="text">
                <span>목차</span>
                <span class="close_button"><i class="fas fa-times"></i></span>
            </div>
            <div class="course_title_wrap">
                <div class="course_real_title">
                    <span class="title">${courseClass.name}</span>
                </div>
                <div class="course_option">
                    <span>수강기간 : 무제한</span>
                    <c:set var="totalClass" value="0"></c:set>
                    <c:set var="totalTime" value="${course.getTotalTime()}"></c:set>
                    <c:set var="currentClass" value="0"></c:set>
                    <c:set var="currentTime" value="0"></c:set>
                    <c:set var="index" value="0"></c:set>
                    <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                        <c:set var="temp" value="${fn:length(section.courseClasses)}"></c:set>
                        <c:forEach var="class_course" items="${section.courseClasses}" varStatus="class_status">
                            <c:set var="tempTime" value="${class_course.seconds}"></c:set>
                            <c:if test="${class_course.id eq courseClass.id}">
                                <c:set var="currentClass" value="${class_status.index+1}"></c:set>
                                <c:set var="currentTime" value="${currentTime + tempTime}"></c:set>
                            </c:if>
                        </c:forEach>
                        <c:set var="totalClass" value="${totalClass+temp}"></c:set>
                    </c:forEach>
                    <div class="course_progress_rate">
                        <span class="">진도율 : </span>
                        <span class="current_class_count">${currentClass}</span>
                        <span>강/</span>
                        <span class="total_class_count">${totalClass}</span>
                        <span>강 (</span>
                        <span class="course_rate">${Math.round((currentClass / totalClass) * 100)}</span>
                        <span>%) | 시간 : </span>
                        <span class="current_study_time"></span>
                        <span>분 / </span>
                        <span class="total_study_time"></span>
                        <span>분</span>
                    </div>
                    <script>
                        timeFormatWrapper('${currentTime}', '.current_study_time');
                        timeFormatWrapper('${totalTime}', '.total_study_time');
                    </script>
                </div>
                <div class="course_progress_wrap">
                    <progress class="course_progress" value="${Math.round((currentClass / totalClass) * 100)}" max="100"/>
                </div>
            </div>
            <div class="course_content_wrap">
                <div class="course_content">
                    <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                        <script>// course_id, section_id, sectionCode
                            createSectionContent('${section.name}', '${course.id}', '${section.id}', '${status.index}');
                        </script>
                        <c:forEach var="course_class" items="${section.courseClasses}" varStatus="classStatus">
                            <script> // name, class_id, sectionCode, classCode, study_time
                                createClassContent('${course_class.name}', '${course_class.id}', '${status.index}', '${classStatus.index}', '${course_class.seconds}');
                            </script>
                        </c:forEach>
                    </c:forEach>
                    <%--                        <div class="section_box">--%>
                    <%--                            <div class="section_title">--%>
                    <%--                                소개--%>
                    <%--                            </div>--%>
                    <%--                            <div class="class_wrap">--%>
                    <%--                                <div class="class_box">--%>
                    <%--                                    <div class="class_icon">--%>
                    <%--                                        <span class="play_icon played"><i class="fas fa-check-circle"></i></span>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div class="class_content">--%>
                    <%--                                        <div class="class_title">강사 소개</div>--%>
                    <%--                                        <div class="class_time">--%>
                    <%--                                            <span><i class="fas fa-play-circle"></i></span>--%>
                    <%--                                            <span>7분</span>--%>
                    <%--                                        </div>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                            </div>--%>
                </div>
            </div>
        </div>
        <div class="study_box_wrap">
            <ul class="study_menu">
                <li class="course_data_list checked"><i class="fas fa-list"></i></li>
                <li class="course_community"><i class="fas fa-comment-dots"></i></li>
                <li class="course_note"><i class="fas fa-pen"></i></li>
            </ul>
        </div>
    </div>
    <div id="video_wrap">
        <div class="top_bar">
            <div class="hover_interaction">
                <div class="course_dashboard">
                <div class="clip_board">
                    <i class="fas fa-clipboard-list"></i>
                </div>
                <div class="dashboard" data-url="${course.url}">
                    강의 대시보드
                </div>

            </div>
                <div class="triangle"></div>
            </div>
            <div class="course_title">
                <span class="course_title_value">${courseClass.name}</span>
                <div class="review_button">
                    <span class="star"><i class="fas fa-star"></i></span>
                    <span>수강평 작성하기</span>
                </div>
            </div>
        </div>
        <div class="video_bg">
            <div class="min_height">
                <video id="myPlayer" class="video-js vjs-default-skin vjs-fill"></video>
            </div>
            <c:if test="${!empty courseClass.mediaPath}">
                <script>
                    loadVideoUrl('${courseClass.mediaPath}');
                    loadVideoJS();
                </script>
            </c:if>
            <div class="instructorMemo_wrap">
                ${courseClass.instructorMemo}
            </div>
        </div>
        <div class="bottom_menu">
            <div class="button_wrap">
                <div class="previous_button">
                    <i class="fas fa-chevron-left"></i> 이전 강의
                </div>
                <div class="check_button">
                    <i class="fas fa-check"></i>
                </div>
                <div class="next_button">
                    <i class="fas fa-chevron-right"></i> 다음 강의
                </div>
            </div>
        </div>
    </div>
</div>
