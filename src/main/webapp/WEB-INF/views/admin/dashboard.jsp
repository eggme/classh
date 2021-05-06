<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-04
  Time: 오전 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/css/views/admin/dashboard.css"/>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/classlist/1.2.20171210/classList.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.2.0/chart.js"></script>

<div class="main_wrap">
    <div class="board_flex_wrap">
        <div class="board_top">
            <div class="admin_notice_board board">
                <div>공지사항</div>
            </div>
        </div>
        <div class="board_7_3_layout flex_layout min_height">
            <div class="board_100_layout board">
                <div class="board_65_layout">
                    <div class="board_title">방문자 요약</div>
                    <div class="board_content height_fixed">
                        <canvas id="line_chart" style="width: 100%;"></canvas>
                        <script>
                            drawingLineChart('line_chart');
                        </script>
                    </div>
                </div>
                <div class="board_35_layout">
                    <div class="board_title">사이트 요약</div>
                    <div class="board_content">
                        <div class="site_desc_flex_column">
                            <div class="today_signup_member_wrap flex">
                                <div class="flex_row_space_between_wrap">
                                    <div class="signup_member_count flex_row">
                                        <div class="signup_member_count_menu">오늘 신규회원</div>
                                        <div class="signup_member_count_value value_left_margin">0</div>
                                    </div>
                                    <div class="signup_member_count_percent">
                                        <div class="signup_member_count_percent_value">-</div>
                                    </div>
                                </div>
                                <div class="signup_member_graph_area admin_graph_setting">
                                    <div class="progress height_10">
                                        <div class="progress-bar bg-info" role="progressbar" style="width: 50%;"
                                             aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="today_visited_member_wrap flex">
                                <div class="flex_row_space_between_wrap">
                                    <div class="visited_count flex_row">
                                        <div class="visited_count_menu">오늘 방문자</div>
                                        <div class="visited_count_value value_left_margin">2</div>
                                    </div>
                                    <div class="visited_count_percent">
                                            <div class="visited_count_percent_value">-65.83%</div>
                                    </div>
                                </div>
                                <div class="visited_graph_area admin_graph_setting">
                                    <div class="progress height_10">
                                        <div class="progress-bar bg-info" role="progressbar" aria-valuenow="0"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="weekly_average_visitor_wrap flex">
                                <div class="flex_row_space_between_wrap">
                                    <div class="weekly_average_visitor_count flex_row">
                                        <div class="weekly_average_visitor_count_menu">주간 방문자</div>
                                        <div class="weekly_average_visitor_count_value value_left_margin">6</div>
                                    </div>
                                    <div class="weekly_average_visitor_count_percent">
                                        <div class="weekly_average_visitor_count_percent_value">14.8%</div>
                                    </div>
                                </div>
                                <div class="weekly_average_visitor_graph_area admin_graph_setting">
                                    <div class="progress height_10">
                                        <div class="progress-bar bg-info" role="progressbar" style="width: 64.8%;"
                                             aria-valuenow="64.8" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="today_uploaded_question_wrap flex">
                                <div class="flex_row_space_between_wrap">
                                    <div class="today_uploaded_question_count flex_row">
                                        <div class="today_uploaded_question_menu">미답변 질문</div>
                                        <div class="today_uploaded_question_value value_left_margin">0</div>
                                    </div>
                                    <div class="today_uploaded_question_percent">
                                        <div class="today_uploaded_question_percent_value">14.8%</div>
                                    </div>
                                </div>
                                <div class="today_uploaded_question_graph_area admin_graph_setting">
                                    <div class="progress height_10">
                                        <div class="progress-bar bg-info" role="progressbar" style="width: 64.8%;"
                                             aria-valuenow="64.8" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="today_paid_count_wrap flex">
                                <div class="flex_row_space_between_wrap">
                                    <div class="today_paid_count flex_row">
                                        <div class="today_paid_menu">오늘 수익</div>
                                        <div class="today_paid_value value_left_margin">2,168,912 원</div>
                                    </div>
                                    <div class="today_paid_percent">
                                        <div class="today_paid_percent_value">7.2%</div>
                                    </div>
                                </div>
                                <div class="today_paid_graph_area admin_graph_setting">
                                    <div class="progress height_10">
                                        <div class="progress-bar bg-info" role="progressbar" style="width: 55.7%;"
                                             aria-valuenow="55.7" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
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
            <div class="board_title">카테고리 별 이용자</div>
            <div class="board_content height_fixed">
                <canvas id="doughnut_chart" style="width: 100%"></canvas>
                <script>
                    drawingDoughnutChart('doughnut_chart');
                </script>
            </div>
        </div>
        <div class="board_5_layout board">
            <div class="board_title">상위 Top 7 강사 별 수익통계</div>
            <div class="board_content height_fixed">
                <canvas id="bar_chart" style="width: 100%"></canvas>
                <script>
                    drawingBarChart('bar_chart');
                </script>
            </div>
        </div>
    </div>
    <div class="board_5_5_layout flex_layout min_height">
        <div class="board_5_layout board">
            <div class="board_title">공지사항</div>
            <div class="board_content">
                <div class="board_content">
                    <div class="notice_wrap">
                        <div class="notice">
                            <div class="notice_status">
                                <div class="notice_status_value">공지</div>
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
                        <div class="notice">
                            <div class="notice_status">
                                <div class="notice_status_value">공지</div>
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
                        <div class="notice">
                            <div class="notice_status">
                                <div class="notice_status_value">공지</div>
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
                        <div class="notice">
                            <div class="notice_status">
                                <div class="notice_status_value">공지</div>
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
                        <div class="notice">
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
                        <div class="notice">
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
                        <div class="notice">
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
                        <div class="notice">
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
        <div class="board_5_layout board">
            <div class="board_title">Q&A</div>
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
    </div>
</div>
