<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-12
  Time: 오후 5:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="/css/courseList.css">

<div class="content">
    <div class="main_content_warp">
        <table class="main_table">
            <tr class="border_tr">
                <th>이미지</th>
                <th>강의명</th>
                <th>평점</th>
                <th>총 수강생</th>
                <th>질문</th>
                <th>가격</th>
                <th>상태</th>
                <th>관리</th>
            </tr>
            <!-- DB 데이터가 들어갈 공간  -->
            <tr class="tr_padding">
                <td><img class="course_thumbnail" src=""></td>
                <td><a class="no_style" href="/courseInfo.do?course_id=&instructor=true"><span>a</span></a></td>
                <td><span>0</span></td>
                <td><span>0</span></td>
                <td><span>0</span></td>
                <td><span>
                                        무료
                                </span></td>
                <td><span>임시저장</span></td>
                <td><button type="button">삭제</button></td>
            </tr>
        </table>
    </div>
</div>