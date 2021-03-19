<%@ page import="me.eggme.classh.entity.Course" %><%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오후 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/courseInfo.css">

<div class="course_info_wrap">
    <div class="real_course_content">
        <div class="course_info course_active">
            <div class="course_detail_description">
                <%-- 강의 짧은글 소개 --%>
                <div class="course_short_description course_gray_form">
                    <h3>이 강의는 <i class="fas fa-lightbulb"></i></h3>
                    <p class="course_short_desc">테스트용 강의입니다.</p>
                </div>
                <%-- 강의 스킬 태그 --%>
                <div class="course_tag_warp course_form">
                    <div class="course_from_menu">
                        ✍<br />
                        이런 걸<br />
                        배워요!
                    </div>
                    <div class="data_area">
                        <div class="data_0">
                            <div class="icon"><i class="fas fa-check"></i></div>
                            <div class="data_text">UI 테스트 값 입니다.</div>
                        </div>
                    </div>
                </div>
                <%-- 강의 본문 --%>
                <div class="long_description">
                </div>
                <%-- 강의 추천인 --%>
                <div class="course_recommend_warp course_form">
                    <div class="course_from_menu">
                        🎓<br/>
                        이런 분들께<br>
                        추천드려요!
                    </div>
                    <div class="data_area">
                        <div class="data_0">
                            <div class="icon"><i class="fas fa-check"></i></div>
                            <div class="data_text">UI 테스트 값 입니다.</div>
                        </div>
                    </div>
                </div>
                    <%-- 강의 추천인 --%>
                    <div class="course_recommend_warp course_form">
                        <div class="course_from_menu">
                            🎓<br/>
                            이런 분들께<br>
                            추천드려요!
                        </div>
                        <div class="data_area">
                            <div class="data_0">
                                <div class="icon"><i class="fas fa-check"></i></div>
                                <div class="data_text">UI 테스트 값 입니다.</div>
                            </div>
                        </div>
                    </div>
                    <%-- 강의 추천인 --%>
                    <div class="course_recommend_warp course_form">
                        <div class="course_from_menu">
                            🎓<br/>
                            이런 분들께<br>
                            추천드려요!
                        </div>
                        <div class="data_area">
                            <div class="data_0">
                                <div class="icon"><i class="fas fa-check"></i></div>
                                <div class="data_text">UI 테스트 값 입니다.</div>
                            </div>
                        </div>
                    </div>
                    <%-- 강의 추천인 --%>
                    <div class="course_recommend_warp course_form">
                        <div class="course_from_menu">
                            🎓<br/>
                            이런 분들께<br>
                            추천드려요!
                        </div>
                        <div class="data_area">
                            <div class="data_0">
                                <div class="icon"><i class="fas fa-check"></i></div>
                                <div class="data_text">UI 테스트 값 입니다.</div>
                            </div>
                        </div>
                    </div>
                    <%-- 강의 추천인 --%>
                    <div class="course_recommend_warp course_form">
                        <div class="course_from_menu">
                            🎓<br/>
                            이런 분들께<br>
                            추천드려요!
                        </div>
                        <div class="data_area">
                            <div class="data_0">
                                <div class="icon"><i class="fas fa-check"></i></div>
                                <div class="data_text">UI 테스트 값 입니다.</div>
                            </div>
                        </div>
                    </div>
                    <%-- 강의 추천인 --%>
                    <div class="course_recommend_warp course_form">
                        <div class="course_from_menu">
                            🎓<br/>
                            이런 분들께<br>
                            추천드려요!
                        </div>
                        <div class="data_area">
                            <div class="data_0">
                                <div class="icon"><i class="fas fa-check"></i></div>
                                <div class="data_text">UI 테스트 값 입니다.</div>
                            </div>
                        </div>
                    </div>
                    <%-- 강의 추천인 --%>
                    <div class="course_recommend_warp course_form">
                        <div class="course_from_menu">
                            🎓<br/>
                            이런 분들께<br>
                            추천드려요!
                        </div>
                        <div class="data_area">
                            <div class="data_0">
                                <div class="icon"><i class="fas fa-check"></i></div>
                                <div class="data_text">UI 테스트 값 입니다.</div>
                            </div>
                        </div>
                    </div>
            </div>
            <%-- 강사 소개 --%>
            <div class="course_instructor_info">
                <div class="instructor_introduce">
                    <div class="introduce_title">안녕하세요 <span class="underline_course"><c:out value="${course.instructor.member.name}"></c:out> <i class="fas fa-external-link-alt"></i></span>입니다.</div>
                    <div class="introduce_value"></div>
                </div>
                <div class="introduce">
                    <img class="instructor_img" src="/imgs/mini_icon_1.png"/>
                    <p class="instructor_name"></p>
                </div>
            </div>
            <%-- 강의 커리큘럼 --%>
            <div class="course_curriculum course_form_margin">
                <h3>교육과정</h3>
                <div class="curriculum_wrap">
                    <div class="curriculum_head"></div>
                    <div class="curriculum_content">
                    </div>
                </div>
            </div>
        </div>
        <div class="course_question">
            <div class="question_btn_wrap">
                <div class="question_button">질문 작성</div>
            </div>
            <%--컨텐츠 로드--%>
            <div class="question_content_wrap_box"></div>
        </div>
        <div class="course_newly"></div>
        <div class="course_management"></div>
    </div>
    <div class="box_wrap">
        <div class="course_box">
            <div class="course_box_warp">
                <div class="course_price">
                </div>
                <div class="course_status">
                    학습중
                </div>
                <div class="learning_box">
                    이어 학습하기
                </div>
                <div class="mini_box">
                    <div class="add_box mini_box_content"><i class="far fa-plus-square line_height"></i> 내 목록 추가</div>
                    <div class="share mini_box_content"><i class="fas fa-share-alt line_height"></i> 공유하기</div>
                </div>
            </div>
            <div class="course_subtext">
                <div class="top_margin">지식공유자 : <span class="instructor_name_tab"><c:out value="${course.instructor.member.name}"></c:out></span></div>
                <%--  총 수업 수 구하는 로직 --%>
                <c:set var="total_section_class_count" value="0"/>
                <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                    <c:forEach var="class_o" items="${section.courseClasses}" varStatus="status">
                        <c:set var="total_section_class_count" value="${total_section_class_count + 1}" />
                    </c:forEach>
                </c:forEach>
                <div class="top_margin ">총 <span class="total_class"><c:out value="${total_section_class_count}"></c:out></span>개 수업 · 총 <span class="course_hour"></span> 시간 <span class="course_minute"></span>분
                </div>
                <div class="top_margin">기간 : 평생 무제한 시청</div>
                <div class="top_margin">수료증 : 발급 강의</div>
                <div class="top_margin">수강 난이도 : <span class="course_level"></span></div>
            </div>
        </div>
    </div>
</div>

