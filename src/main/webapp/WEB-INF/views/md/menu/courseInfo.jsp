<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-18
  Time: 오후 5:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/css/views/md/menu/courseInfo.css">
<script src="/js/views/md/menu/courseInfo.js"></script>

<div class="marin_wrap" data-id="${course.id}">
    <div class="board_flex_wrap">
        <div class="course_toolbox">
            <div class="course_tool_back_button course_tool_button_template">뒤로가기</div>
            <div class="course_tool_submitted_button course_tool_button_template">승인완료</div>
            <div class="course_tool_reject_button course_tool_button_template">승인거절</div>
        </div>
        <div class="course_header_wrap">
            <div class="course_header_left">
                <div class="course_thumbnail_image">
                    <img class="course_thumbnail_image_value" src="${course.courseImg}">
                </div>
            </div>
            <div class="course_header_right">
                <div class="course_header_right_column">
                    <div class="course_title">
                        강의 이름 : <c:out value="${course.name}"/>
                    </div>
                    <div class="course_instructor_name">
                        강사 이름 : <c:out value="${course.instructor.member.memberName}"/>
                    </div>
                    <div class="course_category">
                        카테고리 : <c:out value="${course.courseCategory.getValue()}" />
                    </div>
                    <c:set var="total_section_class_count" value="0"/>
                    <c:set var="total_section_count" value="0"/>
                    <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                        <c:forEach var="class_o" items="${section.courseClasses}" varStatus="status">
                            <c:set var="total_section_class_count" value="${total_section_class_count + 1}"/>
                        </c:forEach>
                        <c:set var="total_section_count" value="${total_section_count + 1}"></c:set>
                    </c:forEach>
                    <div class="course_total_section_count">
                        총 섹션 개수 : <c:out value="${total_section_count}"/>
                    </div>
                    <div class="course_total_class_count">
                        총 수업 개수 : <c:out value="${total_section_class_count}"/>
                    </div>
                    <div class="course_total_time">
                        총 수업 시간 : <span class="course_total_time_value"></span>
                        <script>timeFormatKor('${course.getTotalTime()}', '.course_total_time_value');</script>
                    </div>
                </div>
                <div class="course_header_right_column">
                    <div class="course_level">
                        강의 레벨 : <c:out value="${course.courseLevel.getValue()}"/>
                    </div>
                    <div class="course_price">
                        강의 가격 : <c:out value="${course.price}"/>
                    </div>
                    <div class="course_status">
                        강의 상태 : <c:out value="${course.courseState.getValue()}"/>
                    </div>
                    <div class="course_preview_box" data-id="${course.id}">
                        강의 미리보기
                    </div>
                </div>
            </div>
        </div>
        <div class="course_content_wrap">
            <div class="course_content_left_wrap course_card_template">
                <div class="course_curriculum course_form_margin">
                    <div class="curriculum_area card_area">
                        <div class="row_wrap">
                            <div class="curriculum_text card_title_text">커리큘럼</div>
                        </div>
                    </div>
                    <div class="curriculum_wrap">
                        <div class="curriculum_content">
                            <c:forEach var="section" items="${course.courseSections}" varStatus="section_status">
                                <script>
                                    sectionSetting('${section_status.index}', '${section.name}', '${fn:length(section.courseClasses)}', '${section.getTotalTime()}');
                                </script>
                                <c:forEach var="course_class" items="${section.courseClasses}" varStatus="class_status">
                                    <c:choose>
                                        <c:when test="${course_class.preview eq true}">
                                            <script>
                                                classSetting('${class_status.index}', '${course_class.name}', '${course_class.seconds}', '.section_class_${section_status.index}', true, '${course_class.id}', '${course.id}');
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
            <div class="course_content_right_wrap">
                <div class="course_content_question_warp course_card_template">
                    <div class="question_area card_area">
                        <div class="row_wrap">
                            <div class="question_text card_title_text">질문 & 답변</div>
                        </div>
                    </div>
                </div>
                <div class="course_content_newly_warp course_card_template">
                    <div class="newly_area card_area">
                        <div class="row_wrap">
                            <div class="newly_text card_title_text">새소식</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>