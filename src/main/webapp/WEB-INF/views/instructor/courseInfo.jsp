<%@ page import="me.eggme.classh.domain.entity.Course" %><%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오후 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                                <c:out value="${course.shortDesc}"/>
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
                            <c:when test="${fn:length(course.skillTags) > 0}">
                                <c:forEach var="skillTag" items="${course.skillTags}" varStatus="status">
                                    <div class="tag_${status.index} flex_column">
                                        <div class="icon"><i class="fas fa-check"></i></div>
                                        <div class="data_text"><c:out value="${skillTag.value}"/></div>
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
                                <c:when test="${fn:length(course.recommendations) > 0}">
                                    <c:forEach var="recommed" items="${course.recommendations}" varStatus="status">
                                        <div class="recommendation_${status.index} flex_column">
                                            <div class="icon">
                                                <i class="fas fa-check"></i>
                                            </div>
                                            <div class="data_text">
                                                <c:out value="${recommed.value}"/>
                                            </div>
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
                            <span class="underline_course">
                                <c:out value="${course.instructor.member.nickName}" />
                                <i class="fas fa-external-link-alt"></i></span>입니다.
                        </div>
                    </div>
                    <div class="instructor_value">
                        <img class="instructor_img" src="${course.instructor.member.profile}"/>
                    </div>
                </div>
                <div class="instructor_selfIntroduce">
                    <div class="introduce_value">
                        <c:out value="${course.instructor.member.selfIntroduce}"/>
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
                <div class="rate_score_view_wrap">
                    <div class="rate_score_flex_wrap">
                        <div class="rate_value_wrap">
                            <div class="avg_rate_value">
                                <c:out value="${course.getReviewAvg()}" />
                            </div>
                            <div class="star_value_wrap">
                                <div class="star_rate">
                                    <div class="star_background">
                                        <i class="fas fa-star star_big_size"></i>
                                        <i class="fas fa-star star_big_size"></i>
                                        <i class="fas fa-star star_big_size"></i>
                                        <i class="fas fa-star star_big_size"></i>
                                        <i class="fas fa-star star_big_size"></i>
                                    </div>
                                    <div class="star_value">
                                    <i class="fas fa-star star_big_size"></i>
                                    <i class="fas fa-star star_big_size"></i>
                                    <i class="fas fa-star star_big_size"></i>
                                    <i class="fas fa-star star_big_size"></i>
                                    <i class="fas fa-star star_big_size"></i>
                                </div>
                                </div>
                                <script>
                                    setStarRate('${course.getReviewAvg()}');
                                </script>
                            </div>
                            <div class="review_count">
                                <span class="review_count_value">
                                    <c:out value="${course.getReviewCount()}" />
                                </span>개의 수강평
                            </div>
                        </div>
                        <div class="rate_graph_wrap">
                            <div class="rate_wrap">
                                <div class="rate_5 rate_container">
                                    <div class="rate_5_title rate_title_container">5점</div>
                                    <div class="rate_5_value rate_value_container">
                                        <div class="rate_relative_wrap">
                                            <div class="rate_background"></div>
                                            <div class="rate_value"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="rate_4 rate_container">
                                    <div class="rate_4_title rate_title_container">4점</div>
                                    <div class="rate_4_value rate_value_container">
                                        <div class="rate_relative_wrap">
                                            <div class="rate_background"></div>
                                            <div class="rate_value"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="rate_3 rate_container">
                                    <div class="rate_3_title rate_title_container">3점</div>
                                    <div class="rate_3_value rate_value_container">
                                        <div class="rate_relative_wrap">
                                            <div class="rate_background"></div>
                                            <div class="rate_value"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="rate_2 rate_container">
                                    <div class="rate_2_title rate_title_container">2점</div>
                                    <div class="rate_2_value rate_value_container">
                                        <div class="rate_relative_wrap">
                                            <div class="rate_background"></div>
                                            <div class="rate_value"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="rate_1 rate_container">
                                    <div class="rate_1_title rate_title_container">1점</div>
                                    <div class="rate_1_value rate_value_container">
                                        <div class="rate_relative_wrap">
                                            <div class="rate_background"></div>
                                            <div class="rate_value"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <script>
                                setRatePercent('${course.getRatePercent()}');
                            </script>
                        </div>
                    </div>
                </div>
                <c:set var="isWroteReview" value="false"/>
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal" var="member"/>
                    <c:set var="isWroteReview" value="${course.isWroteReview(member)}"/>
                    <c:choose>
                        <c:when test="${isWroteReview}">
                            <div class="review_list_form">
                                <div class="review_list_filter">
                                    <div class="review_list_filter_title review_list_template">VIEW</div>
                                    <div class="review_list_filter_divider"></div>
                                    <div class="review_list_filter_like review_list_template"><span
                                            class="separator highlight_active">· </span><span
                                            class="review_list_menu_title">좋아요 순</span></div>
                                    <div class="review_list_filter_recently review_list_template"><span
                                            class="separator">· </span><span class="review_list_menu_title">최신 순</span>
                                    </div>
                                    <div class="review_list_filter_high_rate review_list_template"><span
                                            class="separator">· </span><span
                                            class="review_list_menu_title">높은 평점 순</span></div>
                                    <div class="review_list_filter_low_rate review_list_template"><span
                                            class="separator">· </span><span
                                            class="review_list_menu_title">낮은 평점 순</span></div>
                                </div>
                                <div class="review_list_content">
                                    <c:forEach var="review" items="${course.courseReviews}" varStatus="review_status">
                                        <c:set var="mem" value="${review.member}"/>
                                        <div class="reviewer_form" data-id="${review.id}">
                                            <div class="reviewer_content">
                                                <div claass="reviewer_profile">
                                                    <div class="image_wrapper">
                                                        <img class="reviewer_profile_img" src="${mem.profile}">
                                                    </div>
                                                </div>
                                                <div class="reviewer_content_flex_wrap">
                                                    <div class="reviewer_content_value">
                                                        <div class="review_rate_wrap">
                                                            <div class="review_rate_value">
                                                                <ul class="reviewer_star_rate_ul">
                                                                    <c:forEach var="fillStarDraw" begin="1" end="5"
                                                                               step="1">
                                                                        <c:choose>
                                                                            <c:when test="${fillStarDraw le review.rate}">
                                                                                <li class="rate_${fillStarDraw}"><i
                                                                                        class="fas fa-star star_fill"></i>
                                                                                </li>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <li class="rate_${fillStarDraw}"><i
                                                                                        class="fas fa-star"></i></li>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:forEach>
                                                                </ul>
                                                            </div>
                                                            <div class="review_rate_count"></div>
                                                        </div>
                                                        <div class="reviewer_name"><c:out
                                                                value="${mem.nickName}"/></div>
                                                    </div>
                                                    <div class="reviewer_toolbox">
                                                        <c:set var="isWroteMe"
                                                               value="${review.isWroteReview(member)}"></c:set>
                                                        <c:if test="${isWroteMe}">
                                                            <div class="toolbox_icon">
                                                                <i class="fas fa-ellipsis-v"></i>
                                                            </div>
                                                            <div class="toolbox hidden">
                                                                <div class="edit_button_wrap">
                                                                    <div class="edit_icon"><i class="far fa-edit"></i>
                                                                    </div>
                                                                    <div class="edit_text">수정</div>
                                                                </div>
                                                                <div class="delete_button_wrap">
                                                                    <div class="delete_icon"><i
                                                                            class="far fa-trash-alt"></i></div>
                                                                    <div class="delete_text">삭제</div>
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="reviewer_comment">
                                                <c:out value="${review.reviewContent}"/>
                                            </div>
                                            <div class="reviewer_created_date">
                                                <div class="created_date_wrap">
                                                    <div class="review_date">
                                                        <fmt:parseDate var="dateString" value="${review.modify_at}"
                                                                       pattern="yyyy-MM-dd'T'HH:mm" type="both"/>
                                                        <fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd"/>
                                                    </div>
                                                    <div class="review_recommendation">
                                                        &nbsp;· 답글 작성
                                                    </div>
                                                </div>
                                                <div class="reviewer_like_wrap">
                                                    <div class="like_icon"><i class="far fa-heart"></i></div>
                                                    <div clsas="like_count">0</div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="review_form">
                                <form action="/course/${course.id}/add/review" method="post" class="review_real_form">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                                        <textarea class="review_textarea" name="reviewContent"
                                                  placeholder="좋은 수강평을 남겨주시면 지식공유자와 이후 배우는 사람들에게 큰 도움이 됩니다! 포인트도 드려요!! (5자 이상)"></textarea>
                                        <div class="submit_area">
                                            <div class="review_submit">등록</div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <div class="review_list_form">
                        <div class="review_list_filter">
                            <div class="review_list_filter_title review_list_template">VIEW</div>
                            <div class="review_list_filter_divider"></div>
                            <div class="review_list_filter_like review_list_template"><span
                                    class="separator highlight_active">· </span><span class="review_list_menu_title">좋아요 순</span>
                            </div>
                            <div class="review_list_filter_recently review_list_template"><span
                                    class="separator">· </span><span class="review_list_menu_title">최신 순</span></div>
                            <div class="review_list_filter_high_rate review_list_template"><span
                                    class="separator">· </span><span class="review_list_menu_title">높은 평점 순</span></div>
                            <div class="review_list_filter_low_rate review_list_template"><span
                                    class="separator">· </span><span class="review_list_menu_title">낮은 평점 순</span></div>
                        </div>
                        <div class="review_list_content">
                            <c:forEach var="review" items="${course.courseReviews}" varStatus="review_status">
                                <c:set var="mem" value="${review.member}"/>
                                <div class="reviewer_form">
                                    <div class="reviewer_content">
                                        <div claass="reviewer_profile">
                                            <div class="image_wrapper">
                                                <img class="reviewer_profile_img" src="${mem.profile}">
                                            </div>
                                        </div>
                                        <div class="reviewer_content_value">
                                            <div class="review_rate_wrap">
                                                <div class="review_rate_value">
                                                    <ul class="reviewer_star_rate_ul">
                                                        <c:forEach var="fillStarDraw" begin="1" end="5" step="1">
                                                            <c:choose>
                                                                <c:when test="${fillStarDraw le review.rate}">
                                                                    <li class="rate_${fillStarDraw}"><i
                                                                            class="fas fa-star star_fill"></i></li>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <li class="rate_${fillStarDraw}"><i
                                                                            class="fas fa-star"></i></li>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                                <div class="review_rate_count"></div>
                                            </div>
                                            <div class="reviewer_name"><c:out value="${mem.nickName}"/></div>
                                        </div>
                                    </div>
                                    <div class="reviewer_comment">
                                        <c:out value="${review.reviewContent}"/>
                                    </div>
                                    <div class="reviewer_created_date">
                                        <div class="created_date_wrap">
                                            <div class="review_date">
                                                <fmt:parseDate var="dateString" value="${review.modify_at}"
                                                               pattern="yyyy-MM-dd'T'HH:mm" type="both"/>
                                                <fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd"/>
                                            </div>
                                            <div class="review_recommendation">
                                                · 답글 작성
                                            </div>
                                        </div>
                                        <div class="reviewer_like_wrap">
                                            <div class="like_icon"><i class="far fa-heart"></i></div>
                                            <div clsas="like_count">0</div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>
    <div class="box_wrap">
        <div class="course_box">
            <div class="course_box_warp">
                <form action="/course/add/cart" method="post" class="course_cart_form">
                    <input type="hidden" name="course_id" value="${course.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <sec:authorize access="isAuthenticated()">
                        <sec:authentication var="userobject" property="principal"></sec:authentication>
                        <c:choose>
                            <c:when test="${course.isCourseRegistration(userobject)}">
                                <%-- 로그인이 된 상태에서 해당 유저가 해당 강의에 수강신청이 된 상태 --%>
                                <div class="course_price"></div>
                                <div class="course_status">학습중</div>
                                <div class="learning_box">이어 학습하기</div>
                            </c:when>
                            <c:when test="${userobject.isPutInTheCart(course.id)}">
                                <%-- 로그인이 된 상태에서 해당 유저가 해당 강의에 수강신청이 안돼있고 장바구니에 담긴 상태 --%>
                                <c:choose>
                                    <c:when test="${course.price eq 0}">
                                        <div class="course_price">무료</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="course_price">
                                            <script>
                                                CostSeparatorKR('${course.price}', '.course_price');
                                            </script>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="course_status"></div>
                                <div class="learning_box_purchase" data-status="authentication">결제하기</div>
                            </c:when>
                            <c:otherwise>
                                <%-- 로그인은 됐는데 해당 유저가 해당 강의에 수강신청이 안된 상태 --%>
                                <c:choose>
                                    <c:when test="${course.price eq 0}">
                                        <div class="course_price">무료</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="course_price">
                                            <script>
                                                CostSeparatorKR('${course.price}', '.course_price');
                                            </script>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="course_status"></div>
                                <div class="learning_box" data-status="authentication">수강신청</div>
                            </c:otherwise>
                        </c:choose>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <%-- 비로그인 시 익명 사용자 접근 시 --%>
                        <c:choose>
                            <c:when test="${course.price eq 0}">
                                <div class="course_price">무료</div>
                            </c:when>
                            <c:otherwise>
                                <div class="course_price">
                                    <script>
                                        CostSeparatorKR('${course.price}', '.course_price');
                                    </script>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="course_status"></div>
                        <div class="learning_box" data-status="anonymous">수강신청</div>
                    </sec:authorize>
                </form>
                <div class="mini_box">
                    <div class="add_box mini_box_content"><i class="far fa-plus-square line_height"></i> 내 목록 추가</div>
                    <div class="share mini_box_content"><i class="fas fa-share-alt line_height"></i> 공유하기</div>
                </div>
            </div>
            <div class="course_subtext">
                <div class="top_margin">지식공유자 : <span class="instructor_name_tab">
                    <c:out value="${course.instructor.member.nickName}"></c:out></span></div>
                <%--  총 수업 수 구하는 로직 --%>
                <c:set var="total_section_class_count" value="0"/>
                <c:forEach var="section" items="${course.courseSections}" varStatus="status">
                    <c:forEach var="class_o" items="${section.courseClasses}" varStatus="status">
                        <c:set var="total_section_class_count" value="${total_section_class_count + 1}"/>
                    </c:forEach>
                </c:forEach>
                <div class="top_margin ">총 <span class="total_class">
                    <c:out value="${total_section_class_count}"></c:out></span>개 수업 · 총 <span class="course_total_time">
                    <script>timeFormatKorWrapper('${course.getTotalTime()}', '.course_total_time');</script>
                </span>
                </div>
                <div class="top_margin">기간 : 평생 무제한 시청</div>
                <div class="top_margin">수료증 : 발급 강의</div>
                <div class="top_margin">수강 난이도 : <span class="course_level"><c:out
                        value="${course.courseLevel.value}"/></span></div>
            </div>
        </div>
    </div>
</div>

<%-- 댓글 수정 모달 --%>
<div class="edit_review_form_wrap" data-id="">
    <form class="edit_review_form" method="post" action="/course/edit/review">
        <input type="hidden" name="id" value="" class="id">
        <input type="hidden" name="course_id" value="${course.id}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="edit_review_content animate">
            <div class="edit_review_container">
                <div class="edit_review_exit"><i class="fas fa-times exit"></i></div>
                <div class="edit_review_title">힘이 되는 수강평을 남겨주세요!</div>
                <div class="rate_area">
                    <ul class="star_rate_ul sizeUp">
                        <li class="edit_rate_1"><i class="fas fa-star"></i></li>
                        <li class="edit_rate_2"><i class="fas fa-star"></i></li>
                        <li class="edit_rate_3"><i class="fas fa-star"></i></li>
                        <li class="edit_rate_4"><i class="fas fa-star"></i></li>
                        <li class="edit_rate_5"><i class="fas fa-star"></i></li>
                    </ul>
                </div>
                <div class="edit_review_reviewContent">
                    <textarea name="reviewContent" class="reviewContent"></textarea>
                </div>
                <div class="add_class_description_buttons">
                    <div class="edit_review_cancel">취소</div>
                    <div class="edit_review_submit">저장하기</div>
                </div>
            </div>
        </div>
    </form>
</div>