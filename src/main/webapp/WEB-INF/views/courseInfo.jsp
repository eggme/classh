<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오후 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="/css/courseInfo.css">


<div class="course_info_wrap">
    <div class="course_content_area">
    <div class="real_course_content">
        <div class="course_board"></div>
        <div class="course_info course_active">
            <div class="course_detail_description">
                <%-- 강의 짧은글 소개 --%>
                <div class="course_short_description course_form">
                    <h3>이 강의는 <i class="fas fa-lightbulb"></i></h3>
                    <p class="course_short_desc">테스트 기본 값입니다.</p>
                </div>
                <%-- 강의 스킬 태그 --%>
                <div class="course_tag_warp course_form">
                    <h3>이런걸 배워요 <i class="far fa-smile"></i></h3>
                    <ul class="course_tag no_style">테스트 기본 값입니다.</ul>
                </div>
                <%-- 강의 본문 --%>
                <div class="long_description">
                </div>
                <%-- 강의 추천인 --%>
                <div class="course_recommend_warp course_form">
                    <h3>도움 되시는 분들 <i class="far fa-smile"></i></h3>
                    <ul class="course_recommend no_style">테스트 기본 값입니다.</ul>
                </div>
            </div>
            <%-- 강의 공개 일자 --%>
            <div class="course_create_at course_form_margin">
                <h4>공개 일자</h4>
                <p class="create_at"></p>
            </div>
            <%-- 강사 소개 --%>
            <div class="course_instructor_info course_form_margin">
                <h3>지식공유자 소개</h3>
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
</div>
    <div class="box_wrap">
        <div class="course_box">
            <div class="course_price">
            </div>
            <div class="learning_box">
                수강신청
            </div>
            <div class="course_subtext">
                <div class="top_margin">지식공유자 : <span class="instructor_name_tab">백기선</span></div>
                <div class="top_margin "><span class="total_class"></span>회 수업 · 총 <span class="kor_time"></span> 수업
                </div>
                <div class="top_margin">기간 : 평생 무제한 시청</div>
                <div class="top_margin">수료증 : 발급 강의</div>
                <div class="top_margin">수강 난이도 : <span class="course_level"></span></div>
            </div>
            <div>
                <span class="instructor_name_another">백기선</span>의 다른 강의
            </div>
        </div>
    </div>
</div>

