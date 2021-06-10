<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-11
  Time: 오후 2:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/css/dashboard.css">
<script src="/js/views/user/dashboard.js"></script>

<section class="main_section">
    <div class="card_line">
        <%-- 프로필 --%>
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">😀 <c:out value="${member.nickName}"/>님 프로필</div>
                <div class="card_content">
                    <div class="card_content_wrap">
                        <div class="card_logo">
                            <img src="${member.profile}">
                        </div>
                        <div class="card_description">
                            <div class="user_name"><c:out value="${member.nickName}"/>님, 안녕하세요! 👋🏼</div>
                        </div>
                    </div>
                    <div class="card_toolbox_wrap">
                        <div class="edit_profile card_toolbox_button_template" data-url="/member/profile">프로필 수정하기</div>
                    </div>
                </div>
            </div>
        </div>
        <%-- 최근 학습 강의 --%>
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">📖최근 학습 강의</div>
                <div class="card_content">
                    <c:choose>
                        <c:when test="${fn:length(member.courseHistories) gt 0}">
                            <c:set var="courseHistory" value="${member.getLastHistory()}"/>
                            <c:set var="course" value="${courseHistory.course}"/>
                            <%-- 총 수업 수 및 현재까지 완료 수업 시간 계산 --%>
                            <c:set var="totalClass" value="0"/>
                            <c:set var="currentTime" value="0"/>
                            <c:set var="total_section_class_count" value="0"/>
                            <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                                <c:set var="temp" value="${fn:length(section.courseClasses)}"></c:set>
                                <c:set var="totalClass" value="${totalClass + temp}"/>
                            </c:forEach>
                            <c:set var="total_section_class_count" value="${totalClass}"></c:set>
                            <c:forEach var="history" items="${courseHistories}" varStatus="index">
                                <c:if test="${history.startTime ge (history.endTime-10)}">
                                    <c:set var="currentTime" value="${currentTime + history.endTime}"></c:set>
                                </c:if>
                            </c:forEach>

                            <div class="last_course_wrap">
                                <div class="last_course_header">
                                    <div class="last_course_name">
                                        <div class="last_course_name_value">
                                            <c:out value="${courseHistory.course.name}"/>
                                        </div>
                                        <div class="last_course_study_time">
                                            <span class="seprator">(</span>
                                            <span class="time_value"></span>
                                            <script>
                                                timeForTodayObject('${courseHistory.create_at}', ".time_value");
                                            </script>
                                            <span class="seprator">)</span>
                                        </div>
                                    </div>
                                    <div class="course_complete_wrap">
                                        <div class="course_complete_text_wrap">
                                            <div class="course_complete_text">진도율 :&nbsp;</div>
                                            <div class="completed_course_value">
                                                <c:out value="${history.completionCourseCount()}"/>
                                            </div>
                                            <div class="completed_course_subText">강 /</div>
                                            <div class="nonCompleted_course_value">
                                                <c:out value="${total_section_class_count}"/>
                                            </div>
                                            <div class="nonCompleted_course_subText">강 (</div>
                                            <div class="complete_percent_value"></div>
                                            <script>
                                                getPercent('${history.completionCourseCount()}', '${total_section_class_count}', '.complete_percent_value');
                                            </script>
                                            <div class="complete_percent_subText">)</div>
                                        </div>
                                    </div>
                                    <div class="course_progress_wrap">
                                        <progress class="course_progress" value="0" max="100"/>
                                        <script>
                                            getPercentProgress('${history.completionCourseCount()}', '${total_section_class_count}', '.course_progress');
                                        </script>
                                    </div>
                                </div>
                                <div class="last_course_footer">
                                    <div class="my_course_list last_course_button_template" data-url="/member/list">내 모든
                                        강의
                                    </div>
                                    <div class="continuous_last_course last_course_button_template"
                                         data-url="/study/${courseHistory.course.id}/lecture/${courseHistory.courseClass.id}">
                                        이어 학습하기
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="dashboard_flex_center nosearch">최근 학습중인 강의가 없습니다.</div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="card_line">
        <%-- 최근 내 노트 --%>
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">📝최근 내 노트</div>
                <div class="card_content">
                    <c:choose>
                        <c:when test="${false}">

                        </c:when>
                        <c:otherwise>
                            <div class="card_content_wrap nosearch">작성한 노트가 없어요 🙅‍♀️</div>
                            <div class="card_toolbox_wrap">
                                <div class="edit_profile card_toolbox_button_template">내 노트 전체보기</div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <%-- 최근 내 질문 --%>
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">🙋🏻‍♀️최근 내 질문</div>
                <div class="card_content">
                    <div class="card_content_template">
                        <c:choose>
                            <c:when test="${fn:length(member.courseQuestions) gt 0}">
                                <c:forEach var="question" items="${member.courseQuestions}" varStatus="qs">
                                    <div class="dashboard_board_template" data-url="/question/${question.id}">
                                        <div class="dashboard_board_title">
                                            <c:out value="${question.title}"/>
                                        </div>
                                        <div class="dashboard_board_timestamp timestamp_${question.id}"></div>
                                        <script>
                                            timeForTodayObject('${question.create_at}', '.timestamp_${question.id}');
                                        </script>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="card_content_wrap nosearch">궁금한게 있으면 질문을 남겨보세요 🙋‍♀</div>
                            </c:otherwise>
                    </c:choose>
                    </div>
                    <div class="card_toolbox_wrap">
                        <div class="edit_profile card_toolbox_button_template" data-url="/member/questions">내 질문 전체보기</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="card_line">
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">🏃🏻학습 통계</div>
                <div class="card_content">
                    <div class="card_logo">
                        <img src="/imgs/mini_icon_1.png" style="width: 64px; height: 64px;">
                    </div>
                    <div class="card_description">
                        <div class="user_name">이승준님, 즐거운 하루!!!</div>
                        <div class="user_nickname">닉네임 : 이승준</div>
                        <div class="user_email">이메일 : kyyeto9984@naver.com</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">📚최근 학습중인 강의</div>
                <div class="card_content">
                    <c:choose>
                        <c:when test="${false}">

                        </c:when>
                        <c:otherwise>
                            <div class="card_content_wrap nosearch">최근 학습중인 강의가 없습니다.</div>
                            <div class="card_toolbox_wrap">
                                <div class="edit_profile card_toolbox_button_template">내 강의 전체보기</div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="card_line">
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">🎓완료한 강의</div>
                <div class="card_content">
                    <div class="card_logo">
                        <img src="/imgs/mini_icon_1.png" style="width: 64px; height: 64px;">
                    </div>
                    <div class="card_description">
                        <div class="user_name">이승준님, 즐거운 하루!!!</div>
                        <div class="user_nickname">닉네임 : 이승준</div>
                        <div class="user_email">이메일 : kyyeto9984@naver.com</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>