<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-18
  Time: 오전 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/css/instructorDashboard.css">

<section class="main_section">
    <div class="flex_row_wrap">
        <div class="mini_card_wrap MyHome">
            <div class="card_title">My Home</div>
            <div class="card_content font_36">🏡</div>
        </div>
        <div class="mini_card_wrap">
            <div class="card_title">총 수강생 수</div>
            <div class="card_content"><span class="course_total_student highlight">0</span> 명</div>
        </div>
        <div class="mini_card_wrap">
            <div class="card_title">총 강의 수</div>
            <div class="card_content"><span class="course_total_count highlight">0</span> 개</div>
        </div>
        <div class="mini_card_wrap">
            <div class="card_title">평균 평점</div>
            <div class="card_content"><span class="course_average_review highlight">0</span></div>
        </div>
        <div class="mini_card_wrap total_margin">
            <div class="card_title">총 수익</div>
            <div class="card_content"><span class="course_total_margin highlight">0</span></div>
        </div>
    </div>
    <div class="flex_row_wrap">
        <div class="card_wrap_3 card_height">
            <div class="card_title">이번 달 현황</div>
            <div class="card_content"><span class="course_total_margin highlight">0</span> 원</div>
            <div class="card_graph"></div>
        </div>
        <div class="card_wrap_7 card_height">
            <div class="card_title">실시간 수익 현황</div>
            <div class="card_content">
                <table class="margin_table">
                    <tr class="table_head">
                        <th>강의명</th>
                        <th>시간</th>
                        <th>수익</th>
                    </tr>
                    <td></td>
                </table>
            </div>
        </div>
    </div>
    <div class="flex_row_wrap">
        <div class="card_wrap_3 card_height">
            <div class="card_title">강의별 수익 분포</div>
            <div class="card_graph"></div>
        </div>
        <div class="card_wrap_7 card_height">
            <div class="card_title">최근 12주 비교 그래프</div>
        </div>
    </div>
    <div class="flex_row_wrap">
        <div class="card_wrap_10 card_height">
            <div class="card_title">1년 추세 선형 그래프</div>
        </div>
    </div>
</section>