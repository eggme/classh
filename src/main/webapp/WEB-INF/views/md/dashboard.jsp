<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-04
  Time: 오전 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/classlist/1.2.20171210/classList.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.2.0/chart.js"></script>
<script src="/js/views/md/dashboard.js"></script>
<link rel="stylesheet" href="/css/views/md/dashboard.css"/>
<div class="main_wrap">
    <div class="board_flex_wrap">
        <div class="board_top">
            <div class="admin_notice_board board">
                <div>공지사항</div>
            </div>
        </div>
        <div class="board_5_5_layout flex_layout min_height">
            <div class="board_5_layout board">
                <div class="board_title">제출완료 강의</div>
                <div class="board_content">
                    <c:choose>
                        <c:when test="${fn:length(submitList) gt 0}">
                            <c:forEach var="course" items="${submitList}" varStatus="index">
                                <div class="nonpublic_course_box board_base" data-id="${course.id}">
                                    <div class="md_row_wrap">
                                        <div class="md_column_wrap">
                                            <div class="content_wrapper">
                                                <div class="image_wrap">
                                                    <div class="img_border">
                                                        <img src="${course.courseImg}"  class="img_value"/>
                                                    </div>
                                                </div>
                                                <div class="course_content_wrap">
                                                    <div class="course_title_wrap">
                                                        <div class="title">
                                                            <c:out value="${course.name}"/>
                                                        </div>
                                                        <div class="md_row_based">
                                                            <div class="instructor board_sub_text_based">
                                                                <c:out value="${course.instructor.member.nickName}"/>
                                                            </div>
                                                            <div class="separator board_sub_text_based">|</div>
                                                            <div class="timestamp board_sub_text_based">
                                                                <fmt:parseDate var="dateString" value="${course.modify_at}"
                                                                               pattern="yyyy-MM-dd'T'HH:mm" type="both"/>
                                                                <fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd HH:mm"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="status_box">
                                                        <div class="category">
                                                            <c:out value="${course.courseCategory.value}"/>
                                                        </div>
                                                        <div class="status published">
                                                            <c:out value="${course.courseState.value}"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="no_submitted_courses">
                                제출완료된 강의가 없습니다.
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
            <div class="board_5_layout board">
                <div class="board_title">강사 Q&A</div>
                <div class="board_content ">
                    <div class="question_box">
                        <div class="question">
                            <div class="instructor_img_box">
                                <div class="img_box">
                                    <img src="/imgs/default_course_image.png" class="instructor_img_value">
                                </div>
                            </div>
                            <div class="question_box">
                                <div class="question_value_wrapper">
                                    <div class="question_value_area">
                                        <span class="question_icon">Q.</span>
                                        <span class="question_value">질문있습니다.</span>
                                    </div>
                                    <div class="question_tag_area">
                                        <%--  와서 할거 태그 영역에 태그 이미지 넣기 --%>
                                        <div class="tags">
                                            <div class="tag">질문</div>
                                            <div class="tag">게시판</div>
                                            <div class="tag">궁금</div>
                                            <div class="tag">태그</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="instructor_value_wrapper">
                                    <div class="instructor_information">
                                        <div class="instructor_name board_sub_text_based">이승준</div>
                                        <div class="separator board_sub_text_based">|</div>
                                        <div class="timestamp board_sub_text_based">2021년 5월 6일 15:32</div>
                                    </div>
                                    <div class="question_status">
                                        <div class="status non_success_question">미해결</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="question">
                            <div class="instructor_img_box">
                                <div class="img_box">
                                    <img src="/imgs/default_course_image.png" class="instructor_img_value">
                                </div>
                            </div>
                            <div class="question_box">
                                <div class="question_value_wrapper">
                                    <div class="question_value_area">
                                        <span class="question_icon">Q.</span>
                                        <span class="question_value">질문있습니다.</span>
                                    </div>
                                    <div class="question_tag_area">
                                        <%--  와서 할거 태그 영역에 태그 이미지 넣기 --%>
                                        <div class="tags">
                                            <div class="tag">질문</div>
                                            <div class="tag">게시판</div>
                                            <div class="tag">궁금</div>
                                            <div class="tag">태그</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="instructor_value_wrapper">
                                    <div class="instructor_information">
                                        <div class="instructor_name board_sub_text_based">이승준</div>
                                        <div class="separator board_sub_text_based">|</div>
                                        <div class="timestamp board_sub_text_based">2021년 5월 6일 15:32</div>
                                    </div>
                                    <div class="question_status">
                                        <div class="status non_success_question">미해결</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="question">
                            <div class="instructor_img_box">
                                <div class="img_box">
                                    <img src="/imgs/default_course_image.png" class="instructor_img_value">
                                </div>
                            </div>
                            <div class="question_box">
                                <div class="question_value_wrapper">
                                    <div class="question_value_area">
                                        <span class="question_icon">Q.</span>
                                        <span class="question_value">질문있습니다.</span>
                                    </div>
                                    <div class="question_tag_area">
                                        <%--  와서 할거 태그 영역에 태그 이미지 넣기 --%>
                                        <div class="tags">
                                            <div class="tag">질문</div>
                                            <div class="tag">게시판</div>
                                            <div class="tag">궁금</div>
                                            <div class="tag">태그</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="instructor_value_wrapper">
                                    <div class="instructor_information">
                                        <div class="instructor_name board_sub_text_based">이승준</div>
                                        <div class="separator board_sub_text_based">|</div>
                                        <div class="timestamp board_sub_text_based">2021년 5월 6일 15:32</div>
                                    </div>
                                    <div class="question_status">
                                        <div class="status success_question">해결</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="question">
                            <div class="instructor_img_box">
                                <div class="img_box">
                                    <img src="/imgs/default_course_image.png" class="instructor_img_value">
                                </div>
                            </div>
                            <div class="question_box">
                                <div class="question_value_wrapper">
                                    <div class="question_value_area">
                                        <span class="question_icon">Q.</span>
                                        <span class="question_value">질문있습니다.</span>
                                    </div>
                                    <div class="question_tag_area">
                                        <%--  와서 할거 태그 영역에 태그 이미지 넣기 --%>
                                        <div class="tags">
                                            <div class="tag">질문</div>
                                            <div class="tag">궁금</div>
                                            <div class="tag">태그</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="instructor_value_wrapper">
                                    <div class="instructor_information">
                                        <div class="instructor_name board_sub_text_based">이승준</div>
                                        <div class="separator board_sub_text_based">|</div>
                                        <div class="timestamp board_sub_text_based">2021년 5월 6일 15:32</div>
                                    </div>
                                    <div class="question_status">
                                        <div class="status success_question">해결</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="board_5_5_layout flex_layout min_height">
            <div class="board_5_layout board">
                <div class="board_title">강의 별 수익</div>
                <div class="board_content height_fixed">
                    <canvas id="course_chart" style="width: 100%"></canvas>
                    <script>
                        drawingDoughnutChart('course_chart');
                    </script>
                </div>
            </div>
            <div class="board_5_layout board">
                <div class="board_title">강사 별 수익</div>
                <div class="board_content height_fixed">
                    <canvas id="instructor_chart" style="width: 100%"></canvas>
                    <script>
                        drawingDoughnutChart('instructor_chart');
                    </script>
                </div>
            </div>
        </div>
        <div class="board_5_5_layout flex_layout min_height">
            <div class="board_5_layout board">
                <div class="board_title">시스템 Q&A</div>
                <div class="board_content">
                    <div class="qna_wrap">
                        <div class="qna">
                            <div class="qna_data">
                                <div class="qna_title">
                                    <div class="qna_icon">Q.</div>
                                    <div class="qna_value">궁금합니다. 정말로요</div>
                                </div>
                                <div class="qna_user">
                                    <div class="qna_username board_sub_text_based">이승준</div>
                                    <div class="separator board_sub_text_based">|</div>
                                    <div class="qna_timestamp board_sub_text_based">2021년 5월 6일 14:59</div>
                                </div>
                            </div>
                            <div class="qna_status non_success_question">
                                미답변
                            </div>
                        </div>
                        <div class="qna">
                            <div class="qna_data">
                                <div class="qna_title">
                                    <div class="qna_icon">Q.</div>
                                    <div class="qna_value">궁금합니다. 정말로요</div>
                                </div>
                                <div class="qna_user">
                                    <div class="qna_username board_sub_text_based">이승준</div>
                                    <div class="separator board_sub_text_based">|</div>
                                    <div class="qna_timestamp board_sub_text_based">2021년 5월 6일 14:59</div>
                                </div>
                            </div>
                            <div class="qna_status non_success_question">
                                미답변
                            </div>
                        </div>
                        <div class="qna">
                            <div class="qna_data">
                                <div class="qna_title">
                                    <div class="qna_icon">Q.</div>
                                    <div class="qna_value">궁금합니다. 정말로요</div>
                                </div>
                                <div class="qna_user">
                                    <div class="qna_username board_sub_text_based">이승준</div>
                                    <div class="separator board_sub_text_based">|</div>
                                    <div class="qna_timestamp board_sub_text_based">2021년 5월 6일 14:59</div>
                                </div>
                            </div>
                            <div class="qna_status non_success_question">
                                미답변
                            </div>
                        </div>
                        <div class="qna">
                            <div class="qna_data">
                                <div class="qna_title">
                                    <div class="qna_icon">Q.</div>
                                    <div class="qna_value">궁금합니다. 정말로요</div>
                                </div>
                                <div class="qna_user">
                                    <div class="qna_username board_sub_text_based">이승준</div>
                                    <div class="separator board_sub_text_based">|</div>
                                    <div class="qna_timestamp board_sub_text_based">2021년 5월 6일 14:59</div>
                                </div>
                            </div>
                            <div class="qna_status success_question">
                                답변완료
                            </div>
                        </div>
                        <div class="qna">
                            <div class="qna_data">
                                <div class="qna_title">
                                    <div class="qna_icon">Q.</div>
                                    <div class="qna_value">궁금합니다. 정말로요</div>
                                </div>
                                <div class="qna_user">
                                    <div class="qna_username board_sub_text_based">이승준</div>
                                    <div class="separator board_sub_text_based">|</div>
                                    <div class="qna_timestamp board_sub_text_based">2021년 5월 6일 14:59</div>
                                </div>
                            </div>
                            <div class="qna_status success_question">
                                답변완료
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="board_5_layout board">
                <div class="board_title">시스템 공지사항</div>
                <div class="board_content">
                    <div class="system_notice_wrap">
                        <div class="system_notice">
                            <div class="notice_status">
                                <div class="notice">공지</div>
                            </div>
                            <div class="notice_data_wrap">
                                <div class="notice_data">
                                    <div class="notice_tag_data">[시스템]</div>
                                    <div class="notice_data">시스템 공지사항 입니다. 이것은 시스템 공지사항 입니다.</div>
                                </div>
                                <div class="notice_time">
                                2021.05.11
                            </div>
                            </div>
                        </div>
                        <div class="system_notice">
                            <div class="notice_status">
                                <div class="notice">공지</div>
                            </div>
                            <div class="notice_data_wrap">
                                <div class="notice_data">
                                    <div class="notice_tag_data">[시스템]</div>
                                    <div class="notice_data">시스템 공지사항 입니다. 이것은 시스템 공지사항 입니다.</div>
                                </div>
                                <div class="notice_time">
                                    2021.05.11
                                </div>
                            </div>
                        </div>
                        <div class="system_notice">
                            <div class="notice_status">
                                <div class="notice">공지</div>
                            </div>
                            <div class="notice_data_wrap">
                                <div class="notice_data">
                                    <div class="notice_tag_data">[시스템]</div>
                                    <div class="notice_data">시스템 공지사항 입니다. 이것은 시스템 공지사항 입니다.</div>
                                </div>
                                <div class="notice_time">
                                    2021.05.11
                                </div>
                            </div>
                        </div>
                        <div class="system_notice">
                            <div class="notice_status">
                                <div class="notice">공지</div>
                            </div>
                            <div class="notice_data_wrap">
                                <div class="notice_data">
                                    <div class="notice_tag_data">[시스템]</div>
                                    <div class="notice_data">시스템 공지사항 입니다. 이것은 시스템 공지사항 입니다.</div>
                                </div>
                                <div class="notice_time">
                                    2021.05.11
                                </div>
                            </div>
                        </div>
                        <div class="system_notice">
                            <div class="notice_data_wrap">
                                <div class="notice_data">
                                    <div class="notice_tag_data">[시스템]</div>
                                    <div class="notice_data">시스템 공지사항 입니다. 이것은 시스템 공지사항 입니다.</div>
                                </div>
                                <div class="notice_time">
                                    2021.05.11
                                </div>
                            </div>
                        </div>
                        <div class="system_notice">
                            <div class="notice_data_wrap">
                                <div class="notice_data">
                                    <div class="notice_tag_data">[시스템]</div>
                                    <div class="notice_data">시스템 공지사항 입니다. 이것은 시스템 공지사항 입니다.</div>
                                </div>
                                <div class="notice_time">
                                    2021.05.11
                                </div>
                            </div>
                        </div>
                        <div class="system_notice">
                            <div class="notice_data_wrap">
                                <div class="notice_data">
                                    <div class="notice_tag_data">[시스템]</div>
                                    <div class="notice_data">시스템 공지사항 입니다. 이것은 시스템 공지사항 입니다.</div>
                                </div>
                                <div class="notice_time">
                                    2021.05.11
                                </div>
                            </div>
                        </div>
                        <div class="system_notice">
                            <div class="notice_data_wrap">
                                <div class="notice_data">
                                    <div class="notice_tag_data">[시스템]</div>
                                    <div class="notice_data">시스템 공지사항 입니다. 이것은 시스템 공지사항 입니다.</div>
                                </div>
                                <div class="notice_time">
                                    2021.05.11
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>