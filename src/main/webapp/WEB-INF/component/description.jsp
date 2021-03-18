<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오후 2:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="/css/component/description.css" >

<div class="description_wrap">
    <div class="flex_content">
        <div class="course_thumbnail">
            <img class="course_img" src="/imgs/default_course_image.png"/>
            <div class="video_box">
                <div class="play_button">
                    <div class="play_icon"></div>
                </div>
                <div class="play_text">
                    <div class="play_text_title">이어 학습하기</div>
                    <div class="play_text_percent">진도율 : 0강 / 1강 (0.00%)</div>
                </div>
                <div class="video_progress">
                    <div class="progress"></div>
                </div>
            </div>
        </div>
        <div class="flex_column_wrap">
            <div class="content_text">
                <div class="content_title">이 강의는 테스트 강의 입니다</div>
                <div class="content_info">
                    <div class="student_count margin_bottom_10">
                        <span class="icon"><i class="far fa-eye"></i></span>
                        <span class="total_student_count">9999</span>
                        <span class="total_student_count_text">명이 수강하고 있어요!</span>
                    </div>
                    <div class="instructor_information">
                        <span class="icon"><i class="fas fa-chalkboard-teacher"></i></span>
                        <span class="instructor_name icon">관리자</span>
                        <span class="after_icon size12"><i class="fas fa-crown"></i></span>
                    </div>
                    <div class="skill_tag">
                        <div class="icon_div icon"><i class="fas fa-hashtag"></i></div>
                        <div class="tag_wrap">
                            <div class="tag">Java</div>
                            <div class="tag">Spring</div>
                            <div class="tag">Web</div>
                            <div class="tag">Design</div>
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