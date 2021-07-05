<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-18
  Time: 오후 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/css/views/instructor/questionList.css" />
<script src="/js/views/instructor/questionList.js"></script>

<div class="main_wrap">
    <div class="board_flex_wrap">
        <div class="question_search_area">
            <div class="courseState_select_box question_select_box_template">
                <div class="courseState_select_caret_box select_caret_box">
                    <div class="select">해결/미해결</div>
                    <div class="select_caret"><div class="caret"></div></div>
                </div>
                <div class="courseState_select_click_box hidden question_select_box">
                    <div class="complete_and_non_complete select_menu_template">해결/미해결</div>
                    <div class="non_complete select_menu_template">미해결 질문</div>
                    <div class="complete select_menu_template">해결 질문</div>
                </div>
            </div>

            <div class="course_list_select_box question_select_box_template">
                <div class="course_list_select_caret_box select_caret_box">
                    <div class="select">전체강의</div>
                    <div class="select_caret"><div class="caret"></div></div>
                </div>
                <div class="course_list_select_click_box hidden question_select_box">
                    <div class="order_by_course_id select_menu_template">강의번호</div>
                    <div class="order_by_course_name select_menu_template">강의명</div>
                    <div class="order_by_instructor_name select_menu_template">강사이름</div>
                </div>
            </div>

            <div class="question_order_select_box question_select_box_template">
                <div class="question_order_select_caret_box select_caret_box">
                    <div class="select">최신순</div>
                    <div class="select_caret"><div class="caret"></div></div>
                </div>
                <div class="question_order_select_click_box hidden question_select_box">
                    <div class="order_by_create_at select_menu_template">최근 답변 순</div>
                    <div class="order_by_no_item select_menu_template">미답변 순</div>
                    <div class="order_by_like select_menu_template">추천순</div>
                </div>
            </div>
        </div>
    </div>
</div>