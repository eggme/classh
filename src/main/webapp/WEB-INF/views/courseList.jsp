<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-12
  Time: 오후 5:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="/css/courseList.css">
<script src="/js/courseList.js"></script>
<input type="hidden" class="t"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
<div class="course_list_content">
    <div class="main_content_warp">
        <table class="main_table">
            <tr class="border_tr">
                <th class="resize">이미지</th>
                <th>강의명</th>
                <th>평점</th>
                <th>총 수강생</th>
                <th>질문</th>
                <th>가격</th>
                <th>상태</th>
                <th>관리</th>
            </tr>
            <!-- DB 데이터가 들어갈 공간  -->
            <c:forEach var="course" items="${list}">
                <tr class="tr_padding">
                    <td><img class="course_thumbnail" src="${course.courseImg}"></td>
                    <td class="course_id"><a href='/course/${course.url}'><c:out value="${course.name}"/></a></td>
                    <td><span>0</span></td>
                    <td><span>${fn:length(course.signUpCourses)}</span></td>
                    <td><span>0</span></td>
                    <td><span><c:out value="${course.price}"/></span></td>
                    <td><span><c:out value="${course.courseState.value}" /></span></td>
                    <td><button type="button">삭제</button></td>
                </tr>
            </c:forEach>
            <c:if test="${empty list}">
                <tr>
                    <td colspan="8" style="padding-top: 20px;">강의가 없습니다.</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>