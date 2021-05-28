<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-26
  Time: 오전 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="/css/courseDashboard.css">
<script src="/js/courseDashboard.js"></script>

<div class="dashboard_wrap">
    <div class="flex_column_wrap_dashboard">
        <div class="flex_row_wrap_dashboard">
            <div class="my_course_status wid_48_box">
                <div class="title_menu">내 학습상황</div>
                <div class="current_study_status">
                    <div class="menu">
                        <div class="study_count_warp">
                            <span class="current_course_count"><c:out value="${courseHistory.completionCourseCount()}"/></span>
                            <span>/</span>
                            <span class="total_course_count"><c:out value="${course.getTotalClassCount()}"/></span>
                        </div>
                        <div class="study_title">완료 수업</div>
                    </div>
                    <div class="menu">
                        <div class="study_count_warp">
                            <span class="current_course_hour">0</span>
                            <span class="time_separator">h</span>
                            <span class="total_course_minute"></span>
                            <span class="time_separator">m</span>
                            <script>
                                setHours('${course.getTotalTime()}', '.current_course_hour');
                                setMinutes('${course.getTotalTime()}', '.total_course_minute');
                            </script>
                        </div>
                        <div class="study_title">총 학습 시간</div>
                    </div>
                    <div class="menu">
                        <div class="study_count_warp">
                            <span class="course_certification no_certification"><i class="fas fa-trophy"></i></span>
                        </div>
                        <div class="study_title">수료증</div>
                    </div>
                </div>
            </div>
            <div class="recently_my_question wid_48_box">
                <div class="title_menu">최근 질문</div>
            </div>
        </div>
        <div class="curriculum_wrap_dashboard">
            <div class="curriculum_title">교육과정</div>
            <div class="curriculum_toolbox">
                <div class="open_close_toggle">모두 펼치기</div>
                <div class="total_class_count"><c:out value="${course.getTotalClassCount()}"/>강의</div>
            </div>
            <div class="curriculum_content">
                <c:forEach var="section" items="${course.courseSections}" varStatus="section_status">
                    <script>
                        sectionSetting('${section_status.index}', '${section.name}', '${fn:length(section.courseClasses)}', '${section.getTotalTime()}');
                    </script>
                    <c:forEach var="course_class" items="${section.courseClasses}" varStatus="class_status">
                        <c:choose>
                            <c:when test="${course_class.preview eq true}">
                                <script>
                                    classSetting('${class_status.index}', '${course_class.name}', '${course_class.seconds}', '.section_class_${section_status.index}', false, '${course_class.id}', '${course.id}');
                                </script>
                            </c:when>
                            <c:otherwise>
                                <script>
                                    classSetting('${class_status.index}', '${course_class.name}', '${course_class.seconds}', '.section_class_${section_status.index}', false, '${course_class_id}', '${course.id}');
                                </script>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:forEach>
            </div>
        </div>
    </div>

</div>
