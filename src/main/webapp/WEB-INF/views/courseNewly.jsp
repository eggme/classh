<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-26
  Time: 오전 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>

<link rel="stylesheet" href="/css/courseNewly.css" >
<script src="/js/courseNewly.js"></script>
<div class="main_section">
    <div class="real_course_content">
        <div class="search_wrap">
            <div class="button_area">
                <div class="title">새소식 작성하기</div>
            </div>
        </div>
        <div class="news_wrap">
            <c:choose>
                <c:when test="${true}">
                    <div class="news_content_wrap">
                        <div class="news_flex_wrap">
                            <div class="news_profile">
                                <div class="real_profile_wrap">
                                    <img class="real_profile_img" src="/imgs/mini_icon_1.png">
                                </div>
                            </div>
                            <div class="news_content">
                                <div class="news_content_area">
                                    <div class="news_title">ㅋㅋ</div>
                                    <div class="news_toolbar">
                                        <div class="instructor_name">승준</div>
                                        <div class="upload_time">방금</div>
                                        <div class="instructor_tool">
                                            <span class="modify">수정</span>
                                            <span class="delete">삭제</span>
                                        </div>
                                    </div>
                                    <div class="news_content_content">
                                        ㅋㅋㅋㅋㅋㅋㅋ
                                    </div>
                                </div>
                                <div class="comment_wrap">
                                    <div class="comment_like_area">
                                        <div clsas="comment_like_title">이 소식이 도움이 되었나요?</div>
                                        <div class="comment_like_icon"><i class="fas fa-heart"></i></div>
                                        <div clss="comment_like_count">(<span class="real_count">0</span>)</div>
                                    </div>
                                    <div class="comment_textarea_wrap">
                                        <textarea class="comment_textarea" placeholder="내용을 입력해주세요"></textarea>
                                    </div>
                                    <div class="comment_write_button_wrap">
                                        <div class="comment_write_button">댓글 입력</div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="news_content_wrap">
                        <div class="news_flex_wrap">
                            <div class="news_profile">
                                <div class="real_profile_wrap">
                                    <img class="real_profile_img" src="/imgs/mini_icon_1.png">
                                </div>
                            </div>
                            <div class="news_content">
                                <div class="news_content_area">
                                    <div class="news_title">ㅋㅋ</div>
                                    <div class="news_toolbar">
                                        <div class="instructor_name">승준</div>
                                        <div class="upload_time">방금</div>
                                        <div class="instructor_tool">
                                            <span class="modify">수정</span>
                                            <span class="delete">삭제</span>
                                        </div>
                                    </div>
                                    <div class="news_content_content">
                                        ㅋㅋㅋㅋㅋㅋㅋ
                                    </div>
                                </div>
                                <div class="comment_wrap">
                                    <div class="comment_like_area">
                                        <div clsas="comment_like_title">이 소식이 도움이 되었나요?</div>
                                        <div class="comment_like_icon"><i class="fas fa-heart"></i></div>
                                        <div clss="comment_like_count">(<span class="real_count">0</span>)</div>
                                    </div>
                                    <div class="comment_textarea_wrap">
                                        <textarea class="comment_textarea" placeholder="내용을 입력해주세요"></textarea>
                                    </div>
                                    <div class="comment_write_button_wrap">
                                        <div class="comment_write_button">댓글 입력</div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="news_content_wrap">
                        <div class="news_flex_wrap">
                            <div class="news_profile">
                                <div class="real_profile_wrap">
                                    <img class="real_profile_img" src="/imgs/mini_icon_1.png">
                                </div>
                            </div>
                            <div class="news_content">
                                <div class="news_content_area">
                                    <div class="news_title">ㅋㅋ</div>
                                    <div class="news_toolbar">
                                        <div class="instructor_name">승준</div>
                                        <div class="upload_time">방금</div>
                                        <div class="instructor_tool">
                                            <span class="modify">수정</span>
                                            <span class="delete">삭제</span>
                                        </div>
                                    </div>
                                    <div class="news_content_content">
                                        ㅋㅋㅋㅋㅋㅋㅋ
                                    </div>
                                </div>
                                <div class="comment_wrap">
                                    <div class="comment_like_area">
                                        <div clsas="comment_like_title">이 소식이 도움이 되었나요?</div>
                                        <div class="comment_like_icon"><i class="fas fa-heart"></i></div>
                                        <div clss="comment_like_count">(<span class="real_count">0</span>)</div>
                                    </div>
                                    <div class="comment_textarea_wrap">
                                        <textarea class="comment_textarea" placeholder="내용을 입력해주세요"></textarea>
                                    </div>
                                    <div class="comment_write_button_wrap">
                                        <div class="comment_write_button">댓글 입력</div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div clsas="default_news">
                        <div class="no_search">🙈 아직 내용이 없습니다. 🙉</div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
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
                <div class="top_margin">지식공유자 : <span class="instructor_name_tab"><c:out
                        value="${course.instructor.member.name}"></c:out></span></div>
                <%--  총 수업 수 구하는 로직 --%>
                <c:set var="total_section_class_count" value="0"/>
                <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                    <c:forEach var="class_o" items="${section.courseClasses}" varStatus="status">
                        <c:set var="total_section_class_count" value="${total_section_class_count + 1}"/>
                    </c:forEach>
                </c:forEach>
                <div class="top_margin ">총 <span class="total_class"><c:out
                        value="${total_section_class_count}"></c:out></span>개 수업 · 총 <span class="course_total_time">
                    <script>timeFormatKorWrapper('${course.getTotalTime()}', '.course_total_time');</script>
                </span>
                </div>
                <div class="top_margin">기간 : 평생 무제한 시청</div>
                <div class="top_margin">수료증 : 발급 강의</div>
                <div class="top_margin">수강 난이도 : <span class="course_level">${course.courseLevel.value}</span></div>
            </div>
        </div>
    </div>
</div>

<div class="newly_write_form_wrapper">
    <div class="newly_write_form animate">
        <div class="newly_relative_wrap">
            <div class="newly_name_wrap newly_wrap_template">
                <div class="newly_name_menu newly_menu_template">제목</div>
                <div class="newly_name_input newly_input_template">
                    <input type="text" class="newly_name" name="name" placeholder="제목을 입력해주세요.">
                </div>
            </div>
            <div class="newly_tag_wrap newly_wrap_template">
                <div class="newly_tag_menu newly_menu_template">공개 범위</div>
                <div class="newly_tag_input">
                    <div class="newly_wrap_template_row">
                        <div class="radio_wrap">
                            <input type="radio" id="course_public" value="course" /><label for="course_public">수강생들에게 공개</label>
                        </div>
                        <div class="radio_wrap">
                            <input type="radio" id="all_public" value="public"/><label for="all_public">전체 공개</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="newly_content_wrap newly_wrap_template">
                <div class="newly_content_menu newly_menu_template">소식 내용</div>
                <div class="newly_content_input">
                <textarea id="news"></textarea>
                </div>
            </div>
        </div>
        <div class="newly_button_wrap">
            <div class="newly_button_template newly_cancel">취소</div>
            <div class="newly_button_template newly_submit">저장</div>
        </div>
    </div>
</div>