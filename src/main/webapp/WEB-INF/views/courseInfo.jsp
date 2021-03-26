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
<script src="/js/courseInfo.js"></script>

<div class="course_info_wrap">
    <div class="real_course_content">
        <div class="course_info course_active">
            <div class="course_detail_description">
                <%-- 강의 짧은글 소개 --%>
                <div class="course_short_description course_gray_form">
                    <h3>이 강의는 <i class="fas fa-lightbulb"></i></h3>
                    <p class="course_short_desc">
                        <c:choose>
                            <c:when test="${!empty course.shortDesc}">
                                ${course.shortDesc}
                            </c:when>
                            <c:otherwise>
                                테스트용 강의입니다.
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
                <%-- 강의 스킬 태그 --%>
                <div class="course_tag_warp course_form">
                    <div class="course_from_menu">
                        ✍<br/>
                        이런 걸<br/>
                        배워요!
                    </div>
                    <div class="data_area">
                        <c:choose>
                            <c:when test="${fn:length(course.tags) > 0}">
                                <c:forEach var="tag" items="${course.tags}" varStatus="status">
                                    <div class="tag_${status.index} flex_column">
                                        <div class="icon"><i class="fas fa-check"></i></div>
                                        <div class="data_text">${tag.value}</div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="data_0">
                                    <div class="icon"><i class="fas fa-check"></i></div>
                                    <div class="data_text">UI 테스트 값 입니다.</div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <%-- 강의 본문 --%>
                <div class="long_description">
                    <c:if test="${!empty course.longDesc}">
                        ${course.longDesc}
                    </c:if>
                </div>
                <%-- 강의 추천인 --%>
                <div class="course_tip">
                    <div>지식공유자가 알려주는</div>
                    <div class="text_highlight">강의 수강 꿀팁!</div>
                    <div class="course_recommend_warp course_form">
                        <div class="course_from_menu">
                            🎓<br/>
                            이런 분들께<br>
                            추천드려요!
                        </div>
                        <div class="data_area">
                            <c:choose>
                                <c:when test="${fn:length(course.tags) > 0}">
                                    <c:forEach var="recommed" items="${course.recommendations}" varStatus="status">
                                        <div class="recommendation_${status.index} flex_column">
                                            <div class="icon"><i class="fas fa-check"></i></div>
                                            <div class="data_text">${recommed.value}</div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="data_0">
                                        <div class="icon"><i class="fas fa-check"></i></div>
                                        <div class="data_text">UI 테스트 값 입니다.</div>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
            </div>
            <%-- 강사 소개 --%>
            <div class="course_instructor_info">
                <div class="instructor_introduce">
                    <div class="instructor_title">
                        <div class="hello_title">안녕하세요</div>
                        <div class="instructor_name_wrap">
                            <span class="underline_course"><c:out
                                    value="${course.instructor.member.name}"></c:out> <i
                                    class="fas fa-external-link-alt"></i></span>입니다.
                        </div>
                    </div>
                    <div class="instructor_value">
                        <img class="instructor_img" src="${course.instructor.member.profile}"/>
                    </div>
                </div>
                <div class="instructor_selfIntroduce">
                    <div class="introduce_value">
                        ${course.instructor.member.selfIntroduce}
                    </div>
                </div>
            </div>
            <%-- 강의 커리큘럼 --%>
            <div class="course_curriculum course_form_margin">
                <div class="curriculum_area">
                    <div class="row_wrap">
                        <div class="curriculum_text">커리큘럼</div>
                        <div class="curriculum_value">
                            총 <span class="course_total_count">${course.getTotalClassCount()}</span>개 ˙ <span
                                class="course_total_time">
                            <script>timeFormatKorWrapper('${course.getTotalTime()}', '.course_total_time');</script>
                        </span>의 수업
                        </div>
                    </div>
                    <div class="curriculum_toolbar">
                        <div class="curriculum_text_desc">
                            이 강의는 영상, 수업 노트가 제공됩니다. 미리보기를 통해 콘텐츠를 확인해보세요.
                        </div>
                        <div class="curriculum_close_button closed">모두 펼치기</div>
                    </div>
                </div>
                <div class="curriculum_wrap">
                    <div class="curriculum_head"></div>
                    <div class="curriculum_content">
                        <c:forEach var="section" items="${course.courseSections}" varStatus="section_status">
                            <script>
                                sectionSetting('${section_status.index}', '${section.name}', '${fn:length(section.courseClasses)}', '${section.getTotalTime()}');
                            </script>
                            <c:forEach var="course_class" items="${section.courseClasses}" varStatus="class_status">
                                <c:choose>
                                    <c:when test="${course_class.status eq true}">
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

            <%-- 강의 게시일 --%>
            <div class="course_created">
                <div class="createdAndModified">
                    강의게시일 : <span class="course_created_value">
                    <c:choose>
                        <c:when test="${!empty course.create_at}">
                            <script>
                                convertLocalDateTime('${course.create_at}', '.course_created_value');
                            </script>
                        </c:when>
                        <c:otherwise>
                            <script>
                                convertLocalDateTime(new Date(0), '.course_created_value');
                            </script>
                        </c:otherwise>
                    </c:choose>
                </span> (마지막 업데이트일 : <span class="course_modified_value">
                    <c:choose>
                        <c:when test="${!empty course.modify_at}">
                            <script>
                                convertLocalDateTime('${course.modify_at}', '.course_modified_value');
                            </script>
                        </c:when>
                        <c:otherwise>
                            <script>
                                convertLocalDateTime(new Date(0), '.course_modified_value');
                            </script>
                        </c:otherwise>
                    </c:choose>
                </span>)
                </div>
            </div>
            <%-- 강의 수강평 --%>
            <div class="course_review_wrap">
                <div class="review_title_wrap">
                    <div class="review_title">수강평</div>
                    <div class="review_sub_title">수강생분들이 직접 작성하신 수강평입니다. 수강평을 작성 시 300잎이 적립됩니다.</div>
                </div>
                <div class="review_form">
                    <div class="review_rate">
                        <div class="rate_area">
                            <ul class="star_rate_ul">
                                <li class="rate_1"><i class="fas fa-star"></i></li>
                                <li class="rate_2"><i class="fas fa-star"></i></li>
                                <li class="rate_3"><i class="fas fa-star"></i></li>
                                <li class="rate_4"><i class="fas fa-star"></i></li>
                                <li class="rate_5"><i class="fas fa-star"></i></li>
                            </ul>
                        </div>
                        <div class="sub_title_area">별점을 선택해주세요</div>
                    </div>
                    <div class="review_textarea_wrap">
                        <textarea class="review_textarea"
                                  placeholder="좋은 수강평을 남겨주시면 지식공유자와 이후 배우는 사람들에게 큰 도움이 됩니다! 포인트도 드려요!! (5자 이상)"></textarea>
                        <div class="submit_area">
                            <div class="review_submit">등록</div>
                        </div>
                    </div>
                </div>
            </div>
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

